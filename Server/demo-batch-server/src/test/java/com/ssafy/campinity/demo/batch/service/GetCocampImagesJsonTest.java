package com.ssafy.campinity.demo.batch.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafy.campinity.core.entity.campsite.Campsite;
import com.ssafy.campinity.core.entity.campsite.CampsiteImage;
import com.ssafy.campinity.core.repository.campsite.CampsiteImageRepository;
import com.ssafy.campinity.core.repository.campsite.CampsiteRepository;
import com.ssafy.campinity.demo.batch.dto.gocamp.res.ResCampsiteImageDto;
import com.ssafy.campinity.demo.batch.dto.gocamp.res.ResCampsiteListDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.UnsupportedEncodingException;
import java.util.List;

@SpringBootTest
public class GetCocampImagesJsonTest {

    @Autowired
    CampsiteRepository campsiteRepository;

    @Autowired
    CrawlGocampApi crawlGocampApi;

    @Autowired
    CampsiteImageRepository campsiteImageRepository;

    @Test
    @DisplayName("gocamp images json crawling")
    public void getImagesData () throws UnsupportedEncodingException, JsonProcessingException {

        List<Campsite> campsites = campsiteRepository.findAll();

        for (int i = 0; i < campsites.size(); i ++) {
            List<ResCampsiteImageDto> images = crawlGocampApi.getCampsiteImageList(100, campsites.get(i).getContentId());
            if (images == null) {
                continue;
            }
            for (ResCampsiteImageDto image: images) {
                campsiteImageRepository.save(CampsiteImage.builder().imagePath(image.getImageUrl()).
                        campsite(campsites.get(i)).build());
            }
            System.out.println("----------------------------------------------------------------------");
            System.out.println("campsite content id: " + campsites.get(i).getContentId());
            System.out.println("i : " + i);
            System.out.println("----------------------------------------------------------------------");
        }
    }
}
