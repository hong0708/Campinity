package com.ssafy.campinity.demo.batch.dto.gocamp.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqCampsiteDto {

    private String pageNo = "1";

    private String MobileOS = "ETC";

    private String MobileApp = "campinity";

    private String _type = "json";

    public MultiValueMap<String, String> toMultiValueMap(int numOfRows, String serviceKey) throws UnsupportedEncodingException {

        var map = new LinkedMultiValueMap<String, String>();
        map.add(URLEncoder.encode("numOfRows","UTF-8"), URLEncoder.encode(String.valueOf(numOfRows),"UTF-8"));
        map.add(URLEncoder.encode("pageNo","UTF-8"), URLEncoder.encode(pageNo,"UTF-8"));
        map.add(URLEncoder.encode("MobileOS","UTF-8"), URLEncoder.encode(MobileOS,"UTF-8"));
        map.add(URLEncoder.encode("MobileApp","UTF-8"), URLEncoder.encode(MobileApp,"UTF-8"));
        map.add(URLEncoder.encode("serviceKey","UTF-8"), serviceKey);
        map.add(URLEncoder.encode("_type","UTF-8"), URLEncoder.encode(_type,"UTF-8"));

        return map;
    }
}
