package com.ssafy.campinity.core.service;

import org.springframework.stereotype.Service;

import java.util.List;

public interface ChatService {
    List<?> getMyChatRoomList(int memberId);
}
