package com.lioneats.lioneats_backend.dto.google;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class NearByShopIdDTO {

    @JsonProperty("results")
    private List<ShopPlaceIdDTO> results;

    @JsonProperty("next_page_token")
    private String nextPageToken;
}

