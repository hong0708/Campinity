package com.ssafy.campinity.core.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import lombok.*;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import lombok.NoArgsConstructor;
import lombok.Builder;

import java.time.LocalDate;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyCollectionReqDTO {

    @ApiModelProperty(
            value = "캠핑장 이름",
            required = true,
            dataType = "String"
    )
    private String campsiteName;

    @ApiModelProperty(
            value = "여행 날짜",
            required = true,
            dataType = "String"
    )
    private String date;

    @ApiModelProperty(
            value = "컬렉션 글 내용",
            required = true,
            dataType = "String"
    )
    private String content;

    @ApiModelProperty(
            value = "쪽지 이미지(max-file-size: 10MB / max-request-size: 10MB)",
            dataType = "png/ jpeg"
    )
    private MultipartFile file;

    @Builder
    public MyCollectionReqDTO(String campsiteName, String date, String content, MultipartFile file) {
        this.campsiteName = campsiteName;
        this.date = date;
        this.content = content;
        this.file = file;
    }
}
