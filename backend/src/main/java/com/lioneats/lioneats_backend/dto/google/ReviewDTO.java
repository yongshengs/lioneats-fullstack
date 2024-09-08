package com.lioneats.lioneats_backend.dto.google;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class ReviewDTO {

    @JsonProperty("author_name")
    private String authorName;

    @JsonProperty("rating")
    private double rating;

    @JsonProperty("text")
    private String text;
}

