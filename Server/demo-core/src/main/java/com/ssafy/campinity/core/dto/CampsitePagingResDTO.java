package com.ssafy.campinity.core.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CampsitePagingResDTO {

    private int currentPage;

    private int maxPage;

    private List<CampsiteListResDTO> data;

    @Builder
    public CampsitePagingResDTO(int currentPage, int maxPage, List<CampsiteListResDTO> data) {
        this.currentPage = currentPage;
        this.maxPage = maxPage;
        this.data = data;
    }
}
