package com.sotnim.weather;

import java.time.LocalDateTime;

public record WeatherConditionResponse(
        Long id,
        double latitude,
        double longitude,
        LocalDateTime time,
        double temperature,
        double windSpeed,
        double windDirection,
        int weatherCode
) {

    public static WeatherConditionResponse from(WeatherCondition weatherCondition) {
        return new WeatherConditionResponse(
                weatherCondition.getId(),
                weatherCondition.getLatitude(),
                weatherCondition.getLongitude(),
                weatherCondition.getTime(),
                weatherCondition.getTemperature(),
                weatherCondition.getWindSpeed(),
                weatherCondition.getWindDirection(),
                weatherCondition.getWeatherCode()
        );
    }
}
