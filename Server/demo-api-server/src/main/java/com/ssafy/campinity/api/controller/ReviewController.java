package com.ssafy.campinity.api.controller;

import com.ssafy.campinity.core.dto.ReviewReqDTO;
import com.ssafy.campinity.core.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v3/reviews")
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    @PostMapping("")
    public ResponseEntity<Object> createReview(ReviewReqDTO reviewReqDTO) {
        reviewService.createReview(reviewReqDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Object> deleteReview(@PathVariable UUID reviewId) {
        reviewService.deleteReview(reviewId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
