package com.ssafy.campinity.api.controller;


import com.ssafy.campinity.api.config.security.jwt.MemberDetails;
import com.ssafy.campinity.core.dto.ReviewReqDTO;
import com.ssafy.campinity.core.dto.ReviewResDTO;
import com.ssafy.campinity.core.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.naming.NoPermissionException;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v3/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("")
    public ResponseEntity<Object> createReview(
            @AuthenticationPrincipal MemberDetails memberDetails,
            ReviewReqDTO reviewReqDTO) {

        ReviewResDTO result = reviewService.createReview(reviewReqDTO, memberDetails.getMember().getId());
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Object> deleteReview(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @PathVariable UUID reviewId) throws NoPermissionException {

        reviewService.deleteReview(reviewId, memberDetails.getMember().getUuid());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
