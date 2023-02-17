package com.ssafy.campinity.core.dto;

import com.ssafy.campinity.core.utils.EmptyMultiPartFile;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EditMemberInfoReqDTO {

    private String nickName;
    private MultipartFile profileImg;
    private Boolean isChanged;

    @Builder
    public EditMemberInfoReqDTO(String nickName, MultipartFile profileImg, Boolean isChanged) {
        this.nickName = nickName;
        this.isChanged = isChanged;
        EmptyMultiPartFile emptyFile = new EmptyMultiPartFile();
        this.profileImg = profileImg != null ? (MultipartFile) profileImg : emptyFile.create();
    }
}
