package com.sotnim.weather

import com.sotnim.location.Location
import com.sotnim.weather.gateway.OpenMeteoGateway
import com.sotnim.weather.gateway.WeatherBean
import spock.lang.Specification
import spock.lang.Subject

class WeatherConditionProviderSpec extends Specification {

    def producer = Stub(WeatherConditionProducer)
    def gateway = Stub(OpenMeteoGateway)

    @Subject
    def provider = new WeatherConditionProvider(gateway, producer)

    def 'should get weather condition'() {
        given:
        def location = Stub(Location)
        def weatherBean = Stub(WeatherBean)
        def condition = Stub(WeatherCondition)

        and:
        gateway.retrieveWeatherData(location as Location) >> weatherBean
        producer.produceFrom(location, weatherBean) >> condition

        expect:
        condition == provider.getFor(location)
    }
}
