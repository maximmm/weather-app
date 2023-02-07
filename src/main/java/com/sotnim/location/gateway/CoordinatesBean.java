package com.sotnim.location.gateway;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CoordinatesBean {

    @JsonProperty("latitude")
    private double latitude;

    @JsonProperty("longitude")
    private double longitude;
}
