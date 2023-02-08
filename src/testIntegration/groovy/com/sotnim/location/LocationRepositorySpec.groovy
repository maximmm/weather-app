package com.sotnim.location

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import spock.lang.Specification
import spock.lang.Subject

import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD

@AutoConfigureTestDatabase
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
@SpringBootTest
class LocationRepositorySpec extends Specification {
    def IP_ADDRESS = '127.0.0.1'
    def LATITUDE = 10.0d
    def LONGITUDE = 20.0d

    @Subject
    @Autowired
    private LocationRepository repository

    def 'should find by ip'() {
        given:
        def location = createLocation()
        repository.save(location)

        when:
        def result = repository.findByIpAddress(IP_ADDRESS).get()

        then:
        result.getId() == 1L
        result.getVersion() == 0
        result.getCreated()
        result.getUpdated()

        and:
        result.getIpAddress() == IP_ADDRESS
        result.getLatitude() == LATITUDE
        result.getLongitude() == LONGITUDE
    }

    def 'should not find by ip'() {
        given:
        def location = createLocation()
        repository.save(location)

        expect:
        repository.findByIpAddress('123.45.67.8').isEmpty()
    }

    private def createLocation() {
        return new Location(
                ipAddress: IP_ADDRESS,
                latitude: LATITUDE,
                longitude: LONGITUDE
        )
    }
}
