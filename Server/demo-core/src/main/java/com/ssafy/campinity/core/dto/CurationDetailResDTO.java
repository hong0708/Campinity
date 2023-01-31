package com.ssafy.campinity.core.dto;

import com.ssafy.campinity.core.entity.curation.Curation;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CurationDetailResDTO {

    private String curationId;

    private String curationCategory;

    private String title;

    private String content;

    private List<String> imagePaths;

    private Boolean isScraped;

    private String createdAt;

    @Builder
    public CurationDetailResDTO(Curation curation, List<String> imagePaths, Boolean isScraped) {
        this.curationId = curation.getUuid().toString();
        this.curationCategory = curation.getCurationCategory().getParam();
        this.title = curation.getTitle();
        this.content = curation.getContent();
        this.imagePaths = imagePaths;
        this.isScraped = isScraped;
        this.createdAt = curation.getCreatedAt().toString();
    }
}
