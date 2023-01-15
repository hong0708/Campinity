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
public class Theme extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String themeName;

    @OneToMany
    @JoinColumn(name = "theme_id")
    @ToString.Exclude
    private List<CampsiteAndTheme> campsiteAndThemes = new ArrayList<>();

    @Builder
    public Theme(String themeName, List<CampsiteAndTheme> campsiteAndThemes) {
        this.themeName = themeName;
        this.campsiteAndThemes = campsiteAndThemes;
    }
}
