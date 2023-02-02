package com.ssafy.campinity.core.entity.curation;

import com.ssafy.campinity.core.entity.BaseEntity;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Curation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "char(36)")
    @Type(type = "uuid-char")
    private UUID uuid;

    private String title;

    private String content;

    private String firstImagePath;

    @Enumerated(value = EnumType.STRING)
    private CurationCategory curationCategory;

    @Builder
    public Curation(UUID uuid, String title, String content, String firstImagePath, String curationCategory) {
        this.uuid = uuid;
        this.title = title;
        this.content = content;
        this.firstImagePath = firstImagePath;
        this.curationCategory = CurationCategory.fromParam(curationCategory);
    }
}
