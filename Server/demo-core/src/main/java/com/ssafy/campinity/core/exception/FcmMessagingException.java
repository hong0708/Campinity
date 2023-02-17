package com.ssafy.campinity.core.exception;

import lombok.Getter;

@Getter
public class FcmMessagingException extends RuntimeException{
    public FcmMessagingException(String message) {
        super(message);
    }
}
