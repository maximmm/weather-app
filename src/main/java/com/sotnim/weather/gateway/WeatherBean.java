package com.sotnim.weather.gateway;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WeatherBean {

    @JsonProperty("time")
    private LocalDateTime time;

    @JsonProperty("temperature")
    private double temperature;

    @JsonProperty("windspeed")
    private double windSpeed;

    @JsonProperty("winddirection")
    private double windDirection;

    @JsonProperty("weathercode")
    private int weatherCode;
}
