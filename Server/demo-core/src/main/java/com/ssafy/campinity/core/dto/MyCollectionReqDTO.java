package com.ssafy.campinity.core.dto;


import com.ssafy.campinity.core.utils.EmptyMultiPartFile;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
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
    public MyCollectionReqDTO(String campsiteName, String date, String content, Object file) {
        this.campsiteName = campsiteName;
        this.date = date;
        this.content = content;

        EmptyMultiPartFile emptyFile = new EmptyMultiPartFile();
        this.file = file != null ? (MultipartFile) file : emptyFile.create();
    }
}
