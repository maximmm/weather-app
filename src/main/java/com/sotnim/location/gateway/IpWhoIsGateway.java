package com.sotnim.location.gateway;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static org.springframework.web.util.UriComponentsBuilder.fromHttpUrl;

@RequiredArgsConstructor
@Component
public class IpWhoIsGateway {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @SneakyThrows(JsonProcessingException.class)
    public CoordinatesBean retrieveCoordinates(String ip) {
        var uri = uriFrom(ip);
        var json = restTemplate.getForObject(uri, String.class);
        return objectMapper.readValue(json, CoordinatesBean.class);
    }

    private String uriFrom(String ip) {
        return fromHttpUrl("https://ipwho.is")
                .path(ip)
                .encode()
                .toUriString();
    }
}
