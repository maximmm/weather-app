package com.sotnim.weather;

import com.sotnim.location.Location;
import com.sotnim.weather.gateway.WeatherBean;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class WeatherConditionProducer {

    private final WeatherConditionRepository repository;

    public WeatherCondition produceFrom(Location location, WeatherBean weatherBean) {
        var condition = repository.findByLocation(location).orElseGet(WeatherCondition::new);
        condition.updateCoordinates(location);
        condition.updateWeatherData(weatherBean);
        return repository.save(condition);
    }
}
