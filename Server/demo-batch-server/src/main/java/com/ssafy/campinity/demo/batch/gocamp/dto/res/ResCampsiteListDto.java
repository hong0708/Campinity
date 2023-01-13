package com.ssafy.campinity.demo.batch.gocamp.dto.res;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ResCampsiteListDto {
    private Response response;

    @Data
    @NoArgsConstructor
    @JsonNaming(PropertyNamingStrategy.class)
    public class Response{
        private Body body;

        @Data
        @NoArgsConstructor
        public class Body{
            private Items items;

            @Data
            @NoArgsConstructor
            public class Items{
                private List<ResCampsiteDto> item;

            }
        }
    }
}
