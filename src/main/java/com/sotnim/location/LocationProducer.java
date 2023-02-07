package com.sotnim.location;

import com.sotnim.location.gateway.CoordinatesBean;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class LocationProducer {

    private final LocationRepository repository;

    public Location produceFrom(String ip, CoordinatesBean coordinatesBean) {
        var location = repository.findByIpAddress(ip).orElseGet(Location::new);
        location.setIpAddress(ip);
        location.updateCoordinates(coordinatesBean);
        return repository.save(location);
    }
}
