package com.lioneats.lioneats_backend.model;

import javax.persistence.*;
import java.util.List;

import com.lioneats.lioneats_backend.model.google.OpeningHour;
import com.lioneats.lioneats_backend.model.google.Photo;
import com.lioneats.lioneats_backend.model.google.Review;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String placeId;
    private String name;
    private String formattedAddress;
    private String formattedPhoneNumber;
    private double rating;
    private int priceLevel;
    private String websiteUrl;
    private String googleUrl;
    private int userRatingsTotal;
    private double latitude;
    private double longitude;
    private String keyword;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "circle_id")
    private Circle circle;

    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<Photo> photos;

    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<Review> reviews;

    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<OpeningHour> openingHours;
}
