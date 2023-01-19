package com.ssafy.campinity.core.repository.review;


import com.ssafy.campinity.core.entity.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    Optional<Review> findByUuid(UUID reviewId);

    void delete(Review review);
}
