package com.lioneats.lioneats_backend.dto.google;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShopDTO {

    @JsonProperty("place_id")
    private String placeId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("formatted_address")
    private String formattedAddress;

    @JsonProperty("formatted_phone_number")
    private String formattedPhoneNumber;

    @JsonProperty("rating")
    private double rating;

    @JsonProperty("price_level")
    private int priceLevel;

    @JsonProperty("website")
    private String websiteUrl;

    @JsonProperty("url")
    private String googleUrl;

    @JsonProperty("user_ratings_total")
    private int userRatingsTotal;

    @JsonProperty("opening_hours")
    private OpeningHourDTO openingHours;

    @JsonProperty("reviews")
    private List<ReviewDTO> reviews;

    @JsonProperty("photos")
    private List<PhotoDTO> photos;

    private String keyWord;

    @JsonProperty("geometry")
    private GeometryDTO geometry;

    public double getLatitude() {
        return getGeometry() != null && getGeometry().getLocation() != null ? getGeometry().getLocation().getLatitude() : 0;
    }

    public double getLongitude() {
        return getGeometry() != null && getGeometry().getLocation() != null ? getGeometry().getLocation().getLongitude() : 0;
    }

    public void setLatitude(double latitude) {
        if (this.geometry != null && this.geometry.getLocation() != null) {
            this.geometry.getLocation().setLatitude(latitude);
        }
    }

    public void setLongitude(double longitude) {
        if (this.geometry != null && this.geometry.getLocation() != null) {
            this.geometry.getLocation().setLongitude(longitude);
        }
    }
}