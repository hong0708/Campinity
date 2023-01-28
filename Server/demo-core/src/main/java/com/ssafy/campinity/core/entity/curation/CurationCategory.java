package com.ssafy.campinity.core.entity.curation;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
public enum CurationCategory {

    ALL("전체"),
    TUTORIAL("튜토리얼"),
    RECIPE("레시피"),
    PLACERECOMMEND("장소추천");

    private final String param;

    private final String name;

    CurationCategory(String param) {
        this.param = param;
        this.name = name();
    }

    private static final Map<String, CurationCategory> paramMap =
            Arrays.stream(CurationCategory.values())
                    .collect(Collectors.toMap(
                            CurationCategory::getParam,
                            Function.identity()
                    ));

    @JsonCreator
    public static CurationCategory fromParam(String param){
        return Optional.ofNullable(param)
                .map(paramMap::get)
                .orElseThrow(() -> new IllegalArgumentException("message category type이 유효하지 않습니다."));
    }

}
