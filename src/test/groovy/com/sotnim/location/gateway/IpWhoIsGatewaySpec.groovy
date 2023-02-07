package com.sotnim.location.gateway

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.web.client.RestTemplate
import spock.lang.Specification
import spock.lang.Subject

class IpWhoIsGatewaySpec extends Specification {

    def restTemplate = Stub(RestTemplate)
    def objectMapper = Stub(ObjectMapper)

    @Subject
    def gateway = new IpWhoIsGateway(restTemplate, objectMapper)

    def 'should retrieve location coordinates'() {
        given:
        def ip = '127.0.0.1'
        def coordinates = Stub(CoordinatesBean)
        def json = '{}'

        and:
        restTemplate.getForObject("https://ipwho.is/$ip", String.class) >> json
        objectMapper.readValue(json, CoordinatesBean.class) >> coordinates

        expect:
        coordinates == gateway.retrieveCoordinates(ip)
    }
}
