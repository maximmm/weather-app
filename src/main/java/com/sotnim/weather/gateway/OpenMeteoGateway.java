package com.sotnim.weather.gateway;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sotnim.location.Location;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static org.springframework.web.util.UriComponentsBuilder.fromHttpUrl;

@RequiredArgsConstructor
@Component
public class OpenMeteoGateway {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public WeatherBean retrieveWeatherData(Location location) {
        var uri = uriFrom(location);
        var json = restTemplate.getForObject(uri, String.class);
        return extractWeather(json);
    }

    @SneakyThrows(JsonProcessingException.class)
    private WeatherBean extractWeather(String json) {
        var tree = objectMapper.readTree(json).get("current_weather");
        return objectMapper.treeToValue(tree, WeatherBean.class);
    }

    private String uriFrom(Location location) {
        return fromHttpUrl("https://api.open-meteo.com")
                .path("/v1/forecast")
                .queryParam("current_weather", true)
                .queryParam("latitude", location.getLatitude())
                .queryParam("longitude", location.getLongitude())
                .encode()
                .toUriString();
    }
}
