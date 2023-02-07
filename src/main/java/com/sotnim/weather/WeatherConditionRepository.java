package com.sotnim.weather;

import com.sotnim.location.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WeatherConditionRepository extends JpaRepository<WeatherCondition, Long> {

    Optional<WeatherCondition> findByLatitudeAndLongitude(double latitude, double longitude);

    default Optional<WeatherCondition> findByLocation(Location location) {
        return findByLatitudeAndLongitude(location.getLatitude(), location.getLongitude());
    }
}
