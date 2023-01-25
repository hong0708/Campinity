package com.ssafy.campinity.core.service;

import com.ssafy.campinity.core.dto.ReviewReqDTO;
import org.springframework.stereotype.Service;

import javax.naming.NoPermissionException;
import java.util.UUID;

@Service
public interface ReviewService {
    public void createReview(ReviewReqDTO reviewReqDTO, int memberId);

    public void deleteReview(UUID reviewId, UUID memberUuid) throws NoPermissionException;
}
