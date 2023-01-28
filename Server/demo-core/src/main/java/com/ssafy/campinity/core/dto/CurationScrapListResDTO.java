package com.ssafy.campinity.core.dto;

import com.ssafy.campinity.core.entity.curation.Curation;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CurationScrapListResDTO {

    private String curationId;

    private String firstImagePath;

    private String title;

    private Boolean isScraped;

    @Builder
    public CurationScrapListResDTO(Curation curation, Boolean isScraped) {
        this.curationId = curation.getUuid().toString();
        this.firstImagePath = curation.getFirstImagePath();
        this.title = curation.getTitle();
        this.isScraped = isScraped;
    }
}
