package com.lioneats.lioneats_backend;

import com.lioneats.lioneats_backend.model.Shop;
import com.lioneats.lioneats_backend.model.google.Review;
import com.lioneats.lioneats_backend.repository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class ReviewServiceTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    @Transactional
    public void testSaveReview() {
        Review review = new Review();
        review.setAuthorName("John Doe");
        review.setRating(4.5);
        review.setText("Great place! The food was amazing.");
        review.setShop(new Shop()); // Set a Shop object if needed

        Review savedReview = reviewRepository.save(review);

        Optional<Review> retrievedReview = reviewRepository.findById(savedReview.getId());
        assertThat(retrievedReview.isPresent()).isTrue();
        assertThat(retrievedReview.get().getId()).isEqualTo(savedReview.getId());
    }

    @Test
    @Transactional
    public void testFindById() {
        Review review = new Review();
        review.setAuthorName("Jane Smith");
        review.setRating(3.8);
        review.setText("Good experience, but the service could be improved.");
        review.setShop(new Shop());

        Review savedReview = reviewRepository.save(review);

        Optional<Review> foundReview = reviewRepository.findById(savedReview.getId());

        assertThat(foundReview.isPresent()).isTrue();
        assertThat(foundReview.get().getId()).isEqualTo(savedReview.getId());
    }
}