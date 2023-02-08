# weather-app

### Get weather data endpoint

```GET http://localhost:8080/weather``` or ```GET http://localhost:8080/weather?ip={yourIp}```

Some comments:
- Parameter ```ip``` in the request is optional. It was introduced for fast checking of weather data for different IPs. If no parameter is provided, then IP of the request originator will be used. Endpoint returns data about weather conditions for provided IP address

- Cache for weather conditions is evicted every hour at 00 min as the provider (OpenMeteo) refreshes current weather data every hour as well. Property ```spring.caching.eviction.cron.weather-conditions``` is used for provision of the cron expression for cache eviction

- Historical analysis of both queries can be performed using audit tables:
  ```
  locations_aud
  weather_conditions_aud
  revinfo
  ```
- ```ExceptionHandlerAdvice``` is responsible for service resilience as well as for showing more user-friendly message in case of any exception
- Although Jar file is presented in the root directory of the project under ```weather-app-1.0-SNAPSHOT.jar``` name, it can still be created using ```./gradlew bootJar``` command
  
- Project uses Java 17

### H2 Console (embedded db):

http://localhost:8080/h2/

Username: sa

Password:

JDBC URL: jdbc:h2:mem:test
