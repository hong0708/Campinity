package com.ssafy.campinity.demo.batch.dto.gocamp.res;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.class)
public class ResCampsiteImageDto {

    private int contentId;

    private String imageUrl;

}
