package com.ssafy.campinity.demo.batch.dto.gocamp.res;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ResCampsiteImageListDto {
    private ResCampsiteImageListDto.Response response;

    @Data
    @NoArgsConstructor
    @JsonNaming(PropertyNamingStrategy.class)
    public class Response{
        private ResCampsiteImageListDto.Response.Body body;

        @Data
        @NoArgsConstructor
        public class Body{
            private ResCampsiteImageListDto.Response.Body.Items items;

            @Data
            @NoArgsConstructor
            public class Items{
                private List<ResCampsiteImageDto> item;

            }
        }
    }
}
