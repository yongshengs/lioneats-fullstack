package com.lioneats.lioneats_backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "google.api")
@Getter
@Setter
public class GoogleApiPropertiesConfig {

    private String baseUrl;
    private String nearbysearchUrl;
    private String detailsUrl;
    private String photoUrl;
    private String key;

    @Value("${google.api.photo.maxwidth}")
    private int maxWidth;
}

