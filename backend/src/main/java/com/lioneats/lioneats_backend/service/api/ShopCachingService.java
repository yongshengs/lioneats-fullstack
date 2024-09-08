package com.lioneats.lioneats_backend.service.api;

import com.lioneats.lioneats_backend.config.GoogleApiPropertiesConfig;
import com.lioneats.lioneats_backend.dto.google.*;
import com.lioneats.lioneats_backend.mapper.google.ShopMapper;
import com.lioneats.lioneats_backend.model.*;
import com.lioneats.lioneats_backend.repository.ShopRepository;
import com.lioneats.lioneats_backend.service.external.AzureBlobStorageService;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

@Service
public class ShopCachingService {

    private static final Logger log = LoggerFactory.getLogger(ShopCachingService.class);

    private final ShopRepository shopRepository;
    private final RestTemplate restTemplate;
    private final GoogleApiPropertiesConfig googleApiPropertiesConfig;
    private final AzureBlobStorageService azureBlobStorageService;

    @Autowired
    public ShopCachingService(ShopRepository shopRepository, RestTemplate restTemplate,
                              GoogleApiPropertiesConfig googleApiPropertiesConfig,
                              AzureBlobStorageService azureBlobStorageService) {
        this.shopRepository = shopRepository;
        this.restTemplate = restTemplate;
        this.googleApiPropertiesConfig = googleApiPropertiesConfig;
        this.azureBlobStorageService = azureBlobStorageService;
    }

    @Cacheable(value = "restaurantsCache", key = "#circle.id + '-' + #dish.name()")
    public void fetchAndStoreNearbyShops(Circle circle, DishDetail dish) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(googleApiPropertiesConfig.getNearbysearchUrl())
                .queryParam("location", circle.getLatitude() + "," + circle.getLongitude())
                .queryParam("radius", circle.getRadius())
                .queryParam("type", "restaurant")
                .queryParam("keyword", dish.getName())
                .queryParam("key", googleApiPropertiesConfig.getKey());

        fetchAndProcessShops(builder, circle, dish);
    }

    private void fetchAndProcessShops(UriComponentsBuilder builder, Circle circle, DishDetail dish) {
        try {
            String nextPageToken = null;
            do {
                URI uriWithToken = builder.cloneBuilder()
                        .queryParamIfPresent("pagetoken", nextPageToken != null ? java.util.Optional.of(nextPageToken) : java.util.Optional.empty())
                        .build().toUri();

                NearByShopIdDTO response = restTemplate.getForObject(uriWithToken, NearByShopIdDTO.class);

                if (response != null && response.getResults() != null) {
                    log.info("Fetched {} shops from API.", response.getResults().size());

                    for (ShopPlaceIdDTO placeIdDTO : response.getResults()) {
                        ShopDTO shopDTO = fetchShopDetails(placeIdDTO.getPlaceId());
                        if (shopDTO != null) {
                            processShopAndUploadImages(shopDTO, placeIdDTO, circle, dish);
                        } else {
                            log.warn("Shop details not found for place ID: {}", placeIdDTO.getPlaceId());
                        }
                    }
                } else {
                    log.warn("No shops found or API returned an empty response for circle ID: {} and dish: {}", circle.getId(), dish.getName());
                }

                nextPageToken = response != null ? response.getNextPageToken() : null;

                if (nextPageToken != null) {
                    Thread.sleep(2000);
                }
            } while (nextPageToken != null);

        } catch (Exception e) {
            log.error("Error occurred while fetching and storing nearby shops: {}", e.getMessage(), e);
        }
    }

    private void processShopAndUploadImages(ShopDTO shopDTO, ShopPlaceIdDTO placeIdDTO, Circle circle, DishDetail dish) throws Exception {
        // Handle image upload for each photo in the shopDTO
        if (shopDTO.getPhotos() != null && !shopDTO.getPhotos().isEmpty()) {
            for (PhotoDTO photoDTO : shopDTO.getPhotos()) {
                String googlePhotoUrl = String.format("%s?maxwidth=%d&photoreference=%s&key=%s",
                        googleApiPropertiesConfig.getPhotoUrl(),
                        googleApiPropertiesConfig.getMaxWidth(),
                        photoDTO.getPhotoReference(),
                        googleApiPropertiesConfig.getKey());

                // Download the image from Google
                InputStream imageInputStream = new URL(googlePhotoUrl).openStream();
                byte[] imageBytes = IOUtils.toByteArray(imageInputStream);
                imageInputStream.close(); // Always close the InputStream after use

                // Upload the image to Azure Blob Storage using ByteArrayInputStream
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageBytes);
                String blobFileName = placeIdDTO.getPlaceId() + "_" + photoDTO.getPhotoReference() + ".jpg"; // Use a unique filename
                String imageUrl = azureBlobStorageService.uploadFile(blobFileName, byteArrayInputStream, imageBytes.length);

                // Update the photo reference to use the Blob URL instead
                photoDTO.setPhotoReference(imageUrl);
            }
        }

        Shop shop = new Shop();
        ShopMapper.updateShopFromDto(shopDTO, shop);
        shop.setCircle(circle);
        shop.setKeyword(dish.getName());

        log.info("Saving shop: {}", shop.getName());
        shopRepository.save(shop);
    }

    private ShopDTO fetchShopDetails(String placeId) {
        String apiUrl = String.format("%s?place_id=%s&key=%s",
                googleApiPropertiesConfig.getDetailsUrl(), placeId, googleApiPropertiesConfig.getKey());

        try {
            log.info("Fetching shop details for place ID: {}", placeId);
            ShopDetailDTO shopDetailDTO = restTemplate.getForObject(apiUrl, ShopDetailDTO.class);
            return shopDetailDTO != null ? shopDetailDTO.getShopDetail() : null;
        } catch (Exception e) {
            log.error("Error occurred while fetching shop details for place ID {}: {}", placeId, e.getMessage(), e);
            return null;
        }
    }
}

