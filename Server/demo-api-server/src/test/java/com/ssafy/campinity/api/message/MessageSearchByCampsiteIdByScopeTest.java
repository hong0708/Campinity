package com.ssafy.campinity.api.message;

import com.ssafy.campinity.api.CampinityApplication;
import com.ssafy.campinity.core.dto.LatLngDTO;
import com.ssafy.campinity.core.service.MessageService;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@Transactional
@SpringBootTest(classes = CampinityApplication.class)
public class MessageSearchByCampsiteIdByScopeTest {

    @Autowired
    MessageService messageService;
//    @Test
    @DisplayName("캠핑장 아이디 및 위경도 기반 쪽지 검색")
    public void getMessageByCampsiteIdByScope() {


        LatLngDTO latLngDTO = LatLngDTO.builder()
                .topLeftLat("40")
                .topLeftLng("100")
                .bottomRightLat("10")
                .bottomRightLng("140")
                .build();

        LatLngDTO latLngDTO1 = LatLngDTO.builder()
                .topLeftLat("25")
                .topLeftLng("100")
                .bottomRightLat("10")
                .bottomRightLng("140")
                .build();

        assertThat(messageService.getMessagesByCampsiteUuidBetweenLatLng(
                "f17fb425-bce7-49f8-ac29-d24d5fc9b66f", latLngDTO
        ).size(), is(equalTo(5)));

        assertThat(messageService.getMessagesByCampsiteUuidBetweenLatLng(
                "f17fb425-bce7-49f8-ac29-d24d5fc9b66f", latLngDTO1
        ).size(), is(equalTo(2)));

    }
}