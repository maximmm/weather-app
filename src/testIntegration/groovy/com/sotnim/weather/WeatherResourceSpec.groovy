package com.sotnim.weather

import com.sotnim.core.config.ExceptionHandlerAdvice
import org.junit.Before
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.web.client.RestClientException
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static com.sotnim.weather.WeatherConditionResponse.from
import static java.time.LocalDateTime.of
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup

@WebMvcTest
class WeatherResourceSpec extends Specification {

    @Autowired
    private MockMvc mvc

    @SpringBean
    private WeatherResourceService resourceService = Stub(WeatherResourceService)

    @Subject
    def resource = new WeatherResource(resourceService)

    @Before
    void setup() {
        this.mvc = standaloneSetup(resource)
                .setControllerAdvice(new ExceptionHandlerAdvice())
                .build()
    }

    def "should return 'ok' with weather condition response"() {
        given:
        def condition = Stub(WeatherCondition) {
            getId() >> 1L
            getLatitude() >> 10.0
            getLongitude() >> 20.0
            getTime() >> of(1990, 7, 17, 0, 0, 0)
            getTemperature() >> 25.0
            getWindSpeed() >> 3.0
            getWindDirection() >> 5.0
            getWeatherCode() >> 3
        }

        and:
        resourceService.fetchFor('127.0.0.1') >> from(condition)

        expect:
        mvc.perform(get("/weather"))
                .andExpect(status().isOk())
                .andReturn()
                .response
                .contentAsString ==
                """
                    {
                      "id": 1,
                      "latitude": 10.0,
                      "longitude": 20.0,
                      "time": [1990,7,17,0,0],
                      "temperature": 25.0,
                      "windSpeed": 3.0,
                      "windDirection": 5.0,
                      "weatherCode": 3
                    }
                """.replaceAll("\\s", "")
    }

    @Unroll
    def "should return 'bad request' with message '#message'"() {
        given:
        resourceService.fetchFor('127.0.0.1') >> { throw exception }

        expect:
        mvc.perform(get("/weather"))
                .andExpect(status().isBadRequest())
                .andReturn()
                .response
                .contentAsString == message

        where:
        exception                   || message
        new RestClientException("") || "Error during external call"
        new RuntimeException()      || "Something went wrong"
    }
}
