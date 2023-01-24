package com.ssafy.campinity.core.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import lombok.NoArgsConstructor;
import lombok.Builder;

import java.time.LocalDate;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyCollectionReqDTO {

    private String campsiteName;

    private String date;

    private String content;

    private MultipartFile file;

    @Builder
    public MyCollectionReqDTO(String campsiteName, String date, String content, MultipartFile file) {
        this.campsiteName = campsiteName;
        this.date = date;
        this.content = content;
        this.file = file;
    }
}
