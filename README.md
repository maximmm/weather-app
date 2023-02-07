# weather-app
#### 

### Get weather data endpoint

GET http://localhost:8080/weather
or
GET http://localhost:8080/weather?ip={yourIp}

- Parameter 'ip' is optional, introduced for fast checking of weather data for different IPs. If no parameter is provided, then IP of the client will be used. Endpoint returns data about weather conditions for provided IP address.

- Cache for weather condition is evicted every hour at 00 min as the provider (OpenMeteo) refreshes current weather data every hour as well. Property 'spring.caching.eviction.cron.weather-conditions' is used for provision of the cron expression for cache eviction.

- Historical analysis of both queries can be performed using audit tables:
'locations_aud'
'weather_conditions_aud'
'revinfo'

## H2 Console (embedded db):

http://localhost:8080/h2/

Username: sa

Password: 

JDBC URL: jdbc:h2:mem:test
