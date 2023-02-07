package com.sotnim.weather.gateway

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.sotnim.location.Location
import org.springframework.web.client.RestTemplate
import spock.lang.Specification
import spock.lang.Subject

class OpenMeteoGatewaySpec extends Specification {

    def restTemplate = Stub(RestTemplate)
    def objectMapper = Stub(ObjectMapper)

    @Subject
    def gateway = new OpenMeteoGateway(restTemplate, objectMapper)

    def 'should retrieve weather data'() {
        given:
        def location = Stub(Location) {
            getLatitude() >> 10.0
            getLongitude() >> 20.0
        }
        def weatherBean = Stub(WeatherBean)

        and:
        def json = '{}'
        def currentWeatherTree = Stub(JsonNode)
        def jsonTree = Stub(JsonNode) {
            get('current_weather') >> currentWeatherTree
        }

        and:
        restTemplate.getForObject('https://api.open-meteo.com/v1/forecast?current_weather=true&latitude=10.0&longitude=20.0', String.class) >> json
        objectMapper.readTree(json) >> jsonTree
        objectMapper.treeToValue(currentWeatherTree, WeatherBean.class) >> weatherBean

        expect:
        weatherBean == gateway.retrieveWeatherData(location)
    }
}
