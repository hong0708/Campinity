package com.ssafy.campinity.core.entity.message;

import com.ssafy.campinity.core.entity.BaseEntity;
import com.ssafy.campinity.core.entity.user.User;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
public class LikeMessage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Message message;

    @ManyToOne
    private User user;

    @Builder
    public LikeMessage(Message message, User user) {
        this.message = message;
        this.user = user;
    }
}
