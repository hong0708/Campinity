package com.ssafy.campinity.core.service;

import com.ssafy.campinity.core.dto.ReviewReqDTO;
import com.ssafy.campinity.core.dto.ReviewResDTO;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface ReviewService {
    public ReviewResDTO createReview(ReviewReqDTO reviewReqDTO, int requestMemberId);

    public void deleteReview(UUID reviewId, UUID requestMemberId) throws Exception;
}
