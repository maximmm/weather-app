package com.sotnim.location

import com.sotnim.location.gateway.CoordinatesBean
import com.sotnim.location.gateway.IpWhoIsGateway
import spock.lang.Specification
import spock.lang.Subject

class LocationProviderSpec extends Specification {

    def gateway = Stub(IpWhoIsGateway)
    def producer = Stub(LocationProducer)

    @Subject
    def provider = new LocationProvider(gateway, producer)

    def 'should get location'() {
        given:
        def ip = '127.0.0.1'
        def coordinates = Stub(CoordinatesBean)
        def location = Stub(Location)

        and:
        gateway.retrieveCoordinates(ip) >> coordinates
        producer.produceFrom(ip, coordinates) >> location

        expect:
        location == provider.getFor(ip)
    }
}
