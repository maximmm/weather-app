package com.sotnim.location

import com.sotnim.location.gateway.CoordinatesBean
import spock.lang.Specification
import spock.lang.Subject

import static java.util.Optional.empty
import static java.util.Optional.of

class LocationProducerSpec extends Specification {

    def repository = Stub(LocationRepository)

    @Subject
    def producer = new LocationProducer(repository)

    def 'should produce new location'() {
        given:
        def ip = '127.0.0.1'
        def coordinates = Stub(CoordinatesBean) {
            getLatitude() >> 10.0
            getLongitude() >> 20.0
        }

        and:
        repository.findByIpAddress(ip) >> empty()
        repository.save(_ as Location) >> {
            Location location -> location
        }

        when:
        def result = producer.produceFrom(ip, coordinates)

        then:
        result.getIpAddress() == ip
        result.getLatitude() == coordinates.getLatitude()
        result.getLongitude() == coordinates.getLongitude()
    }

    def 'should update existing location'() {
        given:
        def ip = '127.0.0.1'
        def coordinates = Stub(CoordinatesBean)
        def location = Mock(Location)

        and:
        repository.findByIpAddress(ip) >> of(location)
        repository.save(location) >> location

        when:
        def result = producer.produceFrom(ip, coordinates)

        then:
        result == location

        and:
        1 * location.setIpAddress(ip)
        1 * location.updateCoordinates(coordinates)
    }
}
