package com.lioneats.lioneats_backend.dto.google;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class LocationDTO {

    @JsonProperty("lat")
    private double latitude;

    @JsonProperty("lng")
    private double longitude;
}
