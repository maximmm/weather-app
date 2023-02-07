package com.sotnim.weather

import com.sotnim.location.Location
import com.sotnim.location.LocationProvider
import spock.lang.Specification
import spock.lang.Subject

import static java.time.LocalDateTime.now

class WeatherResourceServiceSpec extends Specification {

    def locationProvider = Stub(LocationProvider)
    def weatherConditionProvider = Stub(WeatherConditionProvider)

    @Subject
    def service = new WeatherResourceService(locationProvider, weatherConditionProvider)

    def 'should get weather condition response'() {
        given:
        def ip = '127.0.0.1'
        def location = Stub(Location)
        def condition = Stub(WeatherCondition) {
            getId() >> 1L
            getLatitude() >> 10.0
            getLongitude() >> 20.0
            getTime() >> now()
            getTemperature() >> 25.0
            getWindSpeed() >> 3.0
            getWindDirection() >> 5.0
            getWeatherCode() >> 2
        }

        and:
        locationProvider.getFor(ip) >> location
        weatherConditionProvider.getFor(location) >> condition

        when:
        def result = service.fetchFor(ip)

        then:
        result.id() == condition.getId()
        result.latitude() == condition.getLatitude()
        result.longitude() == condition.getLongitude()
        result.time() == condition.getTime()
        result.temperature() == condition.getTemperature()
        result.windSpeed() == condition.getWindSpeed()
        result.windDirection() == condition.getWindDirection()
        result.weatherCode() == condition.getWeatherCode()
    }
}
