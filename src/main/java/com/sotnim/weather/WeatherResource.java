package com.sotnim.weather;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static java.util.Optional.ofNullable;
import static org.springframework.http.ResponseEntity.ok;

@RequiredArgsConstructor
@RestController
public class WeatherResource {

    private final WeatherResourceService resourceService;

    @GetMapping("/weather")
    public ResponseEntity<?> weather(@RequestParam(required = false) String ip, HttpServletRequest request) {
        var resolvedIp = ofNullable(ip).orElse(request.getRemoteAddr());
        return ok(resourceService.fetchFor(resolvedIp));
    }
}
