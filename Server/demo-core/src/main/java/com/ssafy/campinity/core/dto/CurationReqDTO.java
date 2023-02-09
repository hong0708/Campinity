package com.ssafy.campinity.core.dto;

import com.ssafy.campinity.core.utils.EmptyMultiPartFile;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CurationReqDTO {

    private String title;

    private String content;

    private String curationCategory;

    private List<MultipartFile> files = new ArrayList<>();

    @Builder
    public CurationReqDTO(String title, String content, String curationCategory, List<MultipartFile> files) {
        this.title = title;
        this.content = content;
        this.curationCategory = curationCategory;
        this.files = files;
        for (MultipartFile file : files) {
            EmptyMultiPartFile emptyFile = new EmptyMultiPartFile();
            if (file != null) continue;
            file = (MultipartFile) emptyFile;
        }
    }
}
