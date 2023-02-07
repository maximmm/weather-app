package com.sotnim.weather;

import com.sotnim.location.Location;
import com.sotnim.weather.gateway.OpenMeteoGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class WeatherConditionProvider {

    private final OpenMeteoGateway openMeteoGateway;
    private final WeatherConditionProducer weatherConditionProducer;

    public WeatherCondition getFor(Location location) {
        var weatherBean = openMeteoGateway.retrieveWeatherData(location);
        return weatherConditionProducer.produceFrom(location, weatherBean);
    }
}
