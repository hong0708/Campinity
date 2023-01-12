package com.ssafy.campinity.demo.batch.gocamp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.campinity.demo.batch.gocamp.dto.req.ReqCampsiteDto;
import com.ssafy.campinity.demo.batch.gocamp.dto.res.ResCampsiteListDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.io.UnsupportedEncodingException;

@Component
public class GocampClient {

    @Value("${goCamp.url.search.campsite}")
    private String baseUrl;

    @Value("${goCamp.url.serviceKey}")
    private String serviceKey;

    public ResCampsiteListDto RequestGocampApi(ReqCampsiteDto reqCampsiteDto) throws UnsupportedEncodingException, JsonProcessingException {

        MultiValueMap<String, String> params = reqCampsiteDto.toMultiValueMap(serviceKey);
        ObjectMapper objectMapper = new ObjectMapper();

        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(baseUrl);
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY);
        WebClient wc = WebClient.builder().uriBuilderFactory(factory).baseUrl(baseUrl).build();

        ResponseEntity<String> response = wc
                .get()
                .uri(uri -> uri.queryParams(params).build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(String.class).block();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        ResCampsiteListDto campsiteDto = objectMapper.readValue(response.getBody(), ResCampsiteListDto.class);
//        System.out.println(campsiteDto.getResponse().getBody().getItems().getItem().get(0).getCaravInnerFclty());
//        List<ResCampsiteDto> camsiteList = campsiteDto.getResponse().getBody().getItems().getItem();
//        camsiteList.forEach(camsite -> System.out.println(camsite));
        return campsiteDto;
    }

}
