package com.ssafy.campinity.core.entity.message;


public enum MessageCategory {

    REVIEW("리뷰"),
    ETC("자유");

    private final String messageCategory;

    MessageCategory(String messageCategory) {
        this.messageCategory = messageCategory;
    }

    public String getMessageCategory() { return messageCategory; }
}
