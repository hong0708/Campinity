package com.ssafy.campinity.core.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;
import java.util.List;

@Getter
public class FcmResDto {

    @JsonProperty("multicast_id")
    private Integer multicastId;
    private Integer success;
    private Integer failure;
    private List<Map<String, String>> results;

    @Builder
    public FcmResDto(Integer multicastId, Integer success, Integer failure, List<Map<String, String>> results) {
        this.multicastId = multicastId;
        this.success = success;
        this.failure = failure;
        this.results = results;
    }
}
