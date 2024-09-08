package com.lioneats.lioneats_backend.mapper.google;

import com.lioneats.lioneats_backend.dto.google.GeometryDTO;
import com.lioneats.lioneats_backend.dto.google.LocationDTO;
import com.lioneats.lioneats_backend.dto.google.ShopDTO;
import com.lioneats.lioneats_backend.model.Shop;

public class ShopMapper {

    public static ShopDTO toDTO(Shop shop) {
        if (shop == null) {
            return null;
        }

        ShopDTO shopDTO = new ShopDTO();
        shopDTO.setPlaceId(shop.getPlaceId());
        shopDTO.setName(shop.getName());
        shopDTO.setFormattedAddress(shop.getFormattedAddress());
        shopDTO.setFormattedPhoneNumber(shop.getFormattedPhoneNumber());
        shopDTO.setRating(shop.getRating());
        shopDTO.setPriceLevel(shop.getPriceLevel());
        shopDTO.setWebsiteUrl(shop.getWebsiteUrl());
        shopDTO.setGoogleUrl(shop.getGoogleUrl());
        shopDTO.setUserRatingsTotal(shop.getUserRatingsTotal());
        shopDTO.setKeyWord(shop.getKeyword());


        GeometryDTO geometryDTO = new GeometryDTO();
        LocationDTO locationDTO = new LocationDTO();
        locationDTO.setLatitude(shop.getLatitude());
        locationDTO.setLongitude(shop.getLongitude());
        geometryDTO.setLocation(locationDTO);
        shopDTO.setGeometry(geometryDTO);

        shopDTO.setOpeningHours(OpeningHourMapper.toDTO(shop.getOpeningHours()));
        shopDTO.setReviews(ReviewMapper.toDTOList(shop.getReviews()));
        shopDTO.setPhotos(PhotoMapper.toDTOList(shop.getPhotos()));

        return shopDTO;
    }

    public static Shop toEntity(ShopDTO shopDTO) {
        if (shopDTO == null) {
            return null;
        }

        Shop shop = new Shop();
        updateShopFromDto(shopDTO, shop);

        return shop;
    }

    public static void updateShopFromDto(ShopDTO shopDTO, Shop shop) {
        if (shopDTO == null || shop == null) {
            return;
        }

        shop.setPlaceId(shopDTO.getPlaceId());
        shop.setName(shopDTO.getName());
        shop.setFormattedAddress(shopDTO.getFormattedAddress());
        shop.setFormattedPhoneNumber(shopDTO.getFormattedPhoneNumber());
        shop.setRating(shopDTO.getRating());
        shop.setPriceLevel(shopDTO.getPriceLevel());
        shop.setWebsiteUrl(shopDTO.getWebsiteUrl());
        shop.setGoogleUrl(shopDTO.getGoogleUrl());
        shop.setUserRatingsTotal(shopDTO.getUserRatingsTotal());
        shop.setKeyword(shopDTO.getKeyWord());
        shop.setLatitude(shopDTO.getLatitude());
        shop.setLongitude(shopDTO.getLongitude());

        if (shopDTO.getOpeningHours() != null) {
            shop.setOpeningHours(OpeningHourMapper.toEntity(shopDTO.getOpeningHours(), shop));
        }

        if (shopDTO.getReviews() != null) {
            shop.setReviews(ReviewMapper.toEntityList(shopDTO.getReviews(), shop));
        }

        if (shopDTO.getPhotos() != null) {
            shop.setPhotos(PhotoMapper.toEntityList(shopDTO.getPhotos(), shop));
        }
    }

}
