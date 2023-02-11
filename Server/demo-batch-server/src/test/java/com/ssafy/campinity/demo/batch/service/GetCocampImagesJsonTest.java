package com.ssafy.campinity.demo.batch.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafy.campinity.core.entity.campsite.Campsite;
import com.ssafy.campinity.core.entity.campsite.CampsiteImage;
import com.ssafy.campinity.core.repository.campsite.CampsiteImageRepository;
import com.ssafy.campinity.core.repository.campsite.CampsiteRepository;
import com.ssafy.campinity.demo.batch.dto.gocamp.res.ResCampsiteImageDto;
import com.ssafy.campinity.demo.batch.dto.gocamp.res.ResCampsiteListDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.*;
import java.util.List;
import java.util.UUID;

@SpringBootTest
public class GetCocampImagesJsonTest {

    @Autowired
    CampsiteRepository campsiteRepository;

    @Autowired
    CrawlGocampApi crawlGocampApi;

    @Autowired
    CampsiteImageRepository campsiteImageRepository;


    @DisplayName("gocamp images json crawling")
    public void getImagesData () throws UnsupportedEncodingException, JsonProcessingException {

        List<Campsite> campsites = campsiteRepository.findAll();

        for (int i = 0; i < campsites.size(); i ++) {
            List<ResCampsiteImageDto> images = crawlGocampApi.getCampsiteImageList(100, campsites.get(i).getContentId());
            if (images == null) {
                continue;
            }
            for (ResCampsiteImageDto image: images) {
                campsiteImageRepository.save(CampsiteImage.builder().thumbnailImage("").imagePath(image.getImageUrl()).
                        campsite(campsites.get(i)).build());
            }
            System.out.println("----------------------------------------------------------------------");
            System.out.println("campsite content id: " + campsites.get(i).getContentId());
            System.out.println("i : " + i);
            System.out.println("----------------------------------------------------------------------");
        }
    }

    @DisplayName("thumbnail 추가")
    public void getthumdnailData () throws IOException {

        File note = new File("C:\\Users\\SSAFY\\Desktop\\thumbnail_urls.txt");

        BufferedReader br = new BufferedReader(new FileReader(note));

        String line = "";
        for (int i = 1; (line = br.readLine()) != null; i ++) {
            CampsiteImage image = campsiteImageRepository.findById(i).orElseThrow(IllegalArgumentException::new);
            image.setThumbnailImage(line);
            campsiteImageRepository.save(image);
        }
    }

    @Test
    @DisplayName("thumnail이 정상적으로 들어갔는지 확인")
    public void checkThumnail () {
        for (int i = 1; i < 49256; i++) {
            CampsiteImage image = campsiteImageRepository.findById(i).orElseThrow(IllegalArgumentException::new);
            String[] split1 = image.getImagePath().split("/");
            String[] split2 = image.getThumbnailImage().split("/");
            Assertions.assertThat(split1[split1.length - 1]).isEqualTo(split2[split2.length - 1]);
        }
    }
}
