package com.ssafy.campinity.api.controller;


import com.ssafy.campinity.api.config.security.jwt.MemberDetails;
import com.ssafy.campinity.api.dto.res.ReviewDeleteDTO;
import com.ssafy.campinity.core.dto.ReviewReqDTO;
import com.ssafy.campinity.core.dto.ReviewResDTO;
import com.ssafy.campinity.core.service.ReviewService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.naming.NoPermissionException;
import java.util.UUID;

@Api(tags = "리뷰 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v3/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @ApiResponses({
            @ApiResponse(code = 201, message = "리뷰 생성 성공했을 때 응답"),
            @ApiResponse(code = 400, message = "입력 데이터 부적합(파라미터 이미지 파일 확장자, 타입 및 입력값 부적절 시 응답"),
            @ApiResponse(code = 401, message = "accessToken 부적합 시 응답"),
    })
    @ApiOperation(value = "리뷰 생성 및 리뷰 객체 반환하는 API")
    @PostMapping("")
    public ResponseEntity<ReviewResDTO> createReview(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestBody ReviewReqDTO reviewReqDTO) {

        ReviewResDTO result = reviewService.createReview(reviewReqDTO, memberDetails.getMember().getId());
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "리뷰 삭제 성공 시 응답"),
            @ApiResponse(code = 400, message = "삭제권한이 없거나 리뷰 식별아이디 값 부적절 시 응답"),
            @ApiResponse(code = 401, message = "accessToken 부적합 시 응답"),
    })
    @ApiOperation(value = "리뷰 삭제 API")
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<ReviewDeleteDTO> deleteReview(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @ApiParam(value = "리뷰 식별 아이디", required = true, type = "String")
            @PathVariable UUID reviewId) throws NoPermissionException {

        reviewService.deleteReview(reviewId, memberDetails.getMember().getUuid());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ReviewDeleteDTO.builder().reviewId(reviewId.toString()).build());
    }
}
