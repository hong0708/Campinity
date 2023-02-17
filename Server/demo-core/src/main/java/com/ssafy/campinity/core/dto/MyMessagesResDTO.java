package com.ssafy.campinity.core.dto;

import com.ssafy.campinity.core.entity.message.Message;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyMessagesResDTO {

    @ApiModelProperty(example = "내가 작성한 리뷰 쪽지 리스트")
    private List<MessageResDTO> myReviewMessages = new ArrayList<>();

    @ApiModelProperty(example = "내가 작성한 자유 쪽지 리스트")
    private List<MessageResDTO> myETCMessages = new ArrayList<>();

    @Builder
    public MyMessagesResDTO(List<Message> messages) {

        for (Message m : messages) {
            MessageResDTO messageResDTO = MessageResDTO.builder().message(m).build();
            if (m.getMessageCategory().getParam().equals("리뷰")) {
                this.myReviewMessages.add(messageResDTO);
            }
            else {
                this.myETCMessages.add(messageResDTO);
            }
        }
    }
}
