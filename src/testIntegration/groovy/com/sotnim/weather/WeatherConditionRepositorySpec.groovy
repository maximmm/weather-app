package com.sotnim.weather

import com.sotnim.location.Location
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import spock.lang.Specification
import spock.lang.Subject

import static java.time.LocalDateTime.now
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD

@AutoConfigureTestDatabase
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
@SpringBootTest
class WeatherConditionRepositorySpec extends Specification {
    def LATITUDE = 10.0d
    def LONGITUDE = 20.0d
    def TIME = now()
    def TEMPERATURE = 25.0d
    def WIND_SPEED = 5.0d
    def WIND_DIRECTION = 3.0d
    def WEATHER_CODE = 2

    @Subject
    @Autowired
    private WeatherConditionRepository repository

    def 'should find by location'() {
        given:
        def location = Stub(Location) {
            getLatitude() >> LATITUDE
            getLongitude() >> LONGITUDE
        }
        def condition = createCondition()

        and:
        repository.save(condition)

        when:
        def result = repository.findByLocation(location).get()

        then:
        result.getId() == 1L
        result.getVersion() == 0
        result.getCreated()
        result.getUpdated()

        and:
        result.getLatitude() == LATITUDE
        result.getLongitude() == LONGITUDE
        result.getTime() == TIME
        result.getTemperature() == TEMPERATURE
        result.getWindSpeed() == WIND_SPEED
        result.getWindDirection() == WIND_DIRECTION
        result.getWeatherCode() == WEATHER_CODE
    }

    def 'should not find by location'() {
        given:
        def location = Stub(Location) {
            getLatitude() >> 1.0
            getLongitude() >> 2.0
        }
        def condition = createCondition()

        and:
        repository.save(condition)

        expect:
        repository.findByLocation(location).isEmpty()
    }

    private def createCondition() {
        return new WeatherCondition(
                latitude: LATITUDE,
                longitude: LONGITUDE,
                time: TIME,
                temperature: TEMPERATURE,
                windSpeed: WIND_SPEED,
                windDirection: WIND_DIRECTION,
                weatherCode: WEATHER_CODE
        )
    }
}
