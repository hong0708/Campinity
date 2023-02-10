package com.ssafy.campinity.core.dto;


import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class BatchResponseDTO {

    private List<String> inValidTokens = new ArrayList();

    @Builder
    public BatchResponseDTO(List<String> inValidTokens) {
        this.inValidTokens = inValidTokens;
    }
    public void addInvalidToken(String token){
        this.inValidTokens.add(token);
    }
}
