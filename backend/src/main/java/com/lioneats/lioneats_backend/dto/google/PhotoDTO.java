package com.lioneats.lioneats_backend.dto.google;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class PhotoDTO {

    @JsonProperty("photo_reference")
    private String photoReference;

    @JsonProperty("height")
    private int height;

    @JsonProperty("width")
    private int width;
}
