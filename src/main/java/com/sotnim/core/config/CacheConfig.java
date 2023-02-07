package com.sotnim.core.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
public class CacheConfig {
    public static final String WEATHER_CONDITIONS_CACHE = "weatherConditions";

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager(WEATHER_CONDITIONS_CACHE);
    }

    @CacheEvict(value = WEATHER_CONDITIONS_CACHE, allEntries = true)
    @Scheduled(cron = "${spring.caching.eviction.cron.weather-conditions}")
    public void evictWeatherConditionsCache() {
    }
}
