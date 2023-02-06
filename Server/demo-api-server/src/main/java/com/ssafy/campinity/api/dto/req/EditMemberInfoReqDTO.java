package com.ssafy.campinity.api.dto.req;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EditMemberInfoReqDTO {

    private String nickName;
    private MultipartFile profileImg;
    private Boolean isChanged;

    @Builder
    public EditMemberInfoReqDTO(String nickName, MultipartFile profileImg, Boolean isChanged) {
        this.nickName = nickName;
        this.profileImg = profileImg;
        this.isChanged = isChanged;
    }
}
