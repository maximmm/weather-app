package com.sotnim.weather

import com.sotnim.location.Location
import com.sotnim.weather.gateway.WeatherBean
import spock.lang.Specification
import spock.lang.Subject

import static java.time.LocalDateTime.now
import static java.util.Optional.empty
import static java.util.Optional.of

class WeatherConditionProducerSpec extends Specification {

    def repository = Stub(WeatherConditionRepository)

    @Subject
    def producer = new WeatherConditionProducer(repository)

    def 'should produce new weather condition'() {
        given:
        def location = Stub(Location) {
            getLatitude() >> 10.0
            getLongitude() >> 20.0
        }
        def weatherBean = Stub(WeatherBean) {
            getTime() >> now()
            getTemperature() >> 25.0
            getWindSpeed() >> 1.0
            getWindDirection() >> 2.0
            getWeatherCode() >> 3
        }

        and:
        repository.findByLocation(location) >> empty()
        repository.save(_ as WeatherCondition) >> {
            WeatherCondition condition -> condition
        }

        when:
        def result = producer.produceFrom(location, weatherBean)

        then:
        result.getLatitude() == location.getLatitude()
        result.getLongitude() == location.getLongitude()

        and:
        result.getTime() == weatherBean.getTime()
        result.getTemperature() == weatherBean.getTemperature()
        result.getWindSpeed() == weatherBean.getWindSpeed()
        result.getWindDirection() == weatherBean.getWindDirection()
        result.getWeatherCode() == weatherBean.getWeatherCode()
    }

    def 'should update existing weather condition'() {
        given:
        def location = Stub(Location)
        def weatherBean = Stub(WeatherBean)
        def condition = Mock(WeatherCondition)

        and:
        repository.findByLocation(location) >> of(condition)
        repository.save(condition) >> condition

        when:
        def result = producer.produceFrom(location, weatherBean)

        then:
        result == condition

        and:
        1 * condition.updateCoordinates(location)
        1 * condition.updateWeatherData(weatherBean)
    }
}
