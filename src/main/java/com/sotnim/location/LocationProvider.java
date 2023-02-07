package com.sotnim.location;

import com.sotnim.location.gateway.IpWhoIsGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class LocationProvider {

    private final IpWhoIsGateway ipWhoIsGateway;
    private final LocationProducer locationProducer;

    public Location getFor(String ip) {
        var coordinatesBean = ipWhoIsGateway.retrieveCoordinates(ip);
        return locationProducer.produceFrom(ip, coordinatesBean);
    }
}
