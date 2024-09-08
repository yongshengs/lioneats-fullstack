package com.lioneats.lioneats_backend.mapper.google;

import com.lioneats.lioneats_backend.dto.google.ReviewDTO;
import com.lioneats.lioneats_backend.model.Shop;
import com.lioneats.lioneats_backend.model.google.Review;

import java.util.List;
import java.util.stream.Collectors;

public class ReviewMapper {

    public static ReviewDTO toDTO(Review review) {
        if (review == null) {
            return null;
        }

        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setAuthorName(review.getAuthorName());
        reviewDTO.setRating(review.getRating());
        reviewDTO.setText(review.getText());

        return reviewDTO;
    }

    public static Review toEntity(ReviewDTO reviewDTO) {
        if (reviewDTO == null) {
            return null;
        }

        Review review = new Review();
        updateReviewFromDto(reviewDTO, review);

        return review;
    }

    public static void updateReviewFromDto(ReviewDTO reviewDTO, Review review) {
        if (reviewDTO == null || review == null) {
            return;
        }

        review.setAuthorName(reviewDTO.getAuthorName());
        review.setRating(reviewDTO.getRating());
        review.setText(reviewDTO.getText());
    }

    public static List<ReviewDTO> toDTOList(List<Review> reviews) {
        if (reviews == null) {
            return null;
        }
        return reviews.stream().map(ReviewMapper::toDTO).collect(Collectors.toList());
    }

    public static List<Review> toEntityList(List<ReviewDTO> reviewDTOs, Shop shop) {
        if (reviewDTOs == null) {
            return null;
        }
        return reviewDTOs.stream().map(dto -> {
            Review review = toEntity(dto);
            review.setShop(shop);
            return review;
        }).collect(Collectors.toList());
    }
}

