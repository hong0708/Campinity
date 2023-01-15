package com.ssafy.campinity.core.entity.campsite;


import com.ssafy.campinity.core.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
public class CaravanFclty extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String fcltyName;

    @OneToMany
    @JoinColumn(name = "caravan_fclty_id")
    @ToString.Exclude
    private List<CampsiteAndCaravanFclty> campsiteAndCaravanFclties = new ArrayList<>();

    @Builder
    public CaravanFclty(String fcltyName, List<CampsiteAndCaravanFclty> campsiteAndCaravanFclties) {
        this.fcltyName = fcltyName;
        this.campsiteAndCaravanFclties = campsiteAndCaravanFclties;
    }
}
