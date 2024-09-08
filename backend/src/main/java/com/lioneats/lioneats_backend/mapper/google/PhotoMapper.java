package com.lioneats.lioneats_backend.mapper.google;

import com.lioneats.lioneats_backend.dto.google.PhotoDTO;
import com.lioneats.lioneats_backend.model.Shop;
import com.lioneats.lioneats_backend.model.google.Photo;

import java.util.List;
import java.util.stream.Collectors;

public class PhotoMapper {

    public static PhotoDTO toDTO(Photo photo) {
        if (photo == null) {
            return null;
        }

        PhotoDTO photoDTO = new PhotoDTO();
        photoDTO.setPhotoReference(photo.getImageUrl());  // Changed from photoReference to imageUrl
        photoDTO.setHeight(photo.getHeight());
        photoDTO.setWidth(photo.getWidth());

        return photoDTO;
    }

    public static Photo toEntity(PhotoDTO photoDTO) {
        if (photoDTO == null) {
            return null;
        }

        Photo photo = new Photo();
        updatePhotoFromDto(photoDTO, photo);

        return photo;
    }

    public static void updatePhotoFromDto(PhotoDTO photoDTO, Photo photo) {
        if (photoDTO == null || photo == null) {
            return;
        }

        photo.setImageUrl(photoDTO.getPhotoReference());
        photo.setHeight(photoDTO.getHeight());
        photo.setWidth(photoDTO.getWidth());
    }

    public static List<PhotoDTO> toDTOList(List<Photo> photos) {
        if (photos == null) {
            return null;
        }
        return photos.stream().map(PhotoMapper::toDTO).collect(Collectors.toList());
    }

    public static List<Photo> toEntityList(List<PhotoDTO> photoDTOs, Shop shop) {
        if (photoDTOs == null) {
            return null;
        }
        return photoDTOs.stream().map(dto -> {
            Photo photo = toEntity(dto);
            photo.setShop(shop);
            return photo;
        }).collect(Collectors.toList());
    }
}

