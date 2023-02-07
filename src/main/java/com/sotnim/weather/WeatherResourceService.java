package com.sotnim.weather;

import com.sotnim.location.LocationProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import static com.sotnim.core.config.CacheConfig.WEATHER_CONDITIONS_CACHE;
import static com.sotnim.weather.WeatherConditionResponse.from;

@RequiredArgsConstructor
@Service
public class WeatherResourceService {

    private final LocationProvider locationProvider;
    private final WeatherConditionProvider weatherConditionProvider;

    @Cacheable(WEATHER_CONDITIONS_CACHE)
    @Transactional
    public WeatherConditionResponse fetchFor(String ipAddress) {
        var location = locationProvider.getFor(ipAddress);
        var weatherCondition = weatherConditionProvider.getFor(location);
        return from(weatherCondition);
    }
}
