package com.sotnim.location

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification
import spock.lang.Subject

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
        def location = new Location(
                ipAddress: IP_ADDRESS,
                latitude: LATITUDE,
                longitude: LONGITUDE
        )
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
}
