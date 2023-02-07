package com.sotnim.weather;

import com.sotnim.core.domain.BaseEntity;
import com.sotnim.location.Location;
import com.sotnim.weather.gateway.WeatherBean;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.SEQUENCE;
import static lombok.AccessLevel.NONE;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Audited
@Entity
@Table(name = "weather_conditions")
public class WeatherCondition extends BaseEntity {

    @Setter(NONE)
    @Id
    @SequenceGenerator(name = "weatherConditionsSequence", sequenceName = "weather_conditions_seq", allocationSize = 1)
    @GeneratedValue(generator = "weatherConditionsSequence", strategy = SEQUENCE)
    @Column(name = "id")
    private Long id;

    @EqualsAndHashCode.Include
    @Column(name = "latitude")
    private double latitude;

    @EqualsAndHashCode.Include
    @Column(name = "longitude")
    private double longitude;

    @Column(name = "time")
    private LocalDateTime time;

    @Column(name = "temperature")
    private double temperature;

    @Column(name = "wind_speed")
    private double windSpeed;

    @Column(name = "wind_direction")
    private double windDirection;

    @Column(name = "weather_code")
    private int weatherCode;

    public void updateCoordinates(Location location) {
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
    }

    public void updateWeatherData(WeatherBean data) {
        this.time = data.getTime();
        this.temperature = data.getTemperature();
        this.windSpeed = data.getWindSpeed();
        this.windDirection = data.getWindDirection();
        this.weatherCode = data.getWeatherCode();
    }
}
