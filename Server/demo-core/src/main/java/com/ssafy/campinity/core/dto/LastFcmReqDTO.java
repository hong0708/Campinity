package com.ssafy.campinity.core.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Getter
@NoArgsConstructor
public class LastFcmReqDTO {

    @ApiModelProperty(
            value = "발신자(요청자)의 fcm 토큰 리스트",
            required = true,
            dataType = "List<String>"
    )
    private List<String> senderTokens;
    @ApiModelProperty(
            value = "수신자가 마지막으로 발신자(요청자)에게 보낼 메세지",
            required = true,
            dataType = "String"
    )
    private String body;

    @Builder
    public LastFcmReqDTO(List<String> senderTokens, String body) {
        this.senderTokens = senderTokens;
        this.body = body;
    }
}
