package com.ssafy.campinity.core.entity.message;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;


public enum MessageCategory {

    REVIEW("리뷰"),
    ETC("자유");

    private static final Map<String, MessageCategory> paramMap =
            Arrays.stream(MessageCategory.values())
                    .collect(Collectors.toMap(
                            MessageCategory::getParam,
                            Function.identity()
                    ));

    private final String param;

    MessageCategory(String param){
        this.param = param;
    }

    @JsonCreator
    public static MessageCategory fromParam(String param){
        return Optional.ofNullable(param)
                .map(paramMap::get)
                .orElseThrow(() -> new IllegalArgumentException("message category type이 유효하지 않습니다."));
    }

    @JsonValue
    public String getParam(){
        return this.param;
    }

}
