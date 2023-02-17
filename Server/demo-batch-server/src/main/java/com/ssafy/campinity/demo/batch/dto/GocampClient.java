package com.ssafy.campinity.demo.batch.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.campinity.demo.batch.dto.gocamp.req.ReqCampsiteDto;
import com.ssafy.campinity.demo.batch.dto.gocamp.req.ReqCampsiteImageDto;
import com.ssafy.campinity.demo.batch.dto.gocamp.res.ResCampsiteImageListDto;
import com.ssafy.campinity.demo.batch.dto.gocamp.res.ResCampsiteListDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.io.UnsupportedEncodingException;

@Component
public class GocampClient {

    @Value("${goCamp.url.search.campsite}")
    private String baseUrl;

    @Value("${goCamp.url.search.image}")
    private String baseImageUrl;


    @Value("${goCamp.url.serviceKey}")
    private String serviceKey;

    public ResCampsiteListDto RequestGocampApi(int numOfRows, ReqCampsiteDto reqCampsiteDto) throws UnsupportedEncodingException, JsonProcessingException {

        MultiValueMap<String, String> params = reqCampsiteDto.toMultiValueMap(numOfRows, serviceKey);
        ObjectMapper objectMapper = new ObjectMapper();
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(baseUrl);
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY);
        ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(-1))
                .build();

        WebClient wc = WebClient.builder()
                .exchangeStrategies(exchangeStrategies)
                .uriBuilderFactory(factory)
                .baseUrl(baseUrl)
                .build();
        ResponseEntity<String> response = wc
                .get()
                .uri(uri -> uri.queryParams(params).build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(String.class)
                .block();

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        ResCampsiteListDto campsiteList = objectMapper.readValue(response.getBody(), ResCampsiteListDto.class);

        return campsiteList;
    }

    public ResCampsiteImageListDto RequestGocampImageApi(int numOfRows, int contentId, ReqCampsiteImageDto reqCampsiteImageDto) throws UnsupportedEncodingException, JsonProcessingException {

        MultiValueMap<String, String> params = reqCampsiteImageDto.toMultiValueMap(numOfRows, serviceKey, contentId);
        ObjectMapper objectMapper = new ObjectMapper();
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(baseImageUrl);
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY);
        ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(-1))
                .build();

        WebClient wc = WebClient.builder()
                .exchangeStrategies(exchangeStrategies)
                .uriBuilderFactory(factory)
                .baseUrl(baseImageUrl)
                .build();
        ResponseEntity<String> response = wc
                .get()
                .uri(uri -> uri.queryParams(params).build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(String.class)
                .block();

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        ResCampsiteImageListDto campsiteImageList = objectMapper.readValue(response.getBody(), ResCampsiteImageListDto.class);

        return campsiteImageList;
    }

}
