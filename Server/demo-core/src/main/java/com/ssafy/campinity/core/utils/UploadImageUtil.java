package com.ssafy.campinity.core.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Component
public class UploadImageUtil {

    public String uploadImage(MultipartFile multipartFile, String table) throws IOException {

        String imagePath = "";

        if (multipartFile.isEmpty()){
            return imagePath;
        }

        String absolutePath = new File("demo-core\\src\\main\\resources\\static").getAbsolutePath() + File.separator + File.separator;
        String path = "images" + File.separator + table;
        File file = new File(absolutePath + path);

        if (!file.exists()) {
            boolean isDirCreated = file.mkdirs();
            if (!isDirCreated)
                System.out.println("file: was not successful");
        }

        String originalFileExtension;
        String contentType = multipartFile.getContentType();

        if (ObjectUtils.isEmpty(contentType)){
            System.out.println("contentType is not vaild");
            return "";
        }
        else {
            if (contentType.contains("image/jpeg"))
                originalFileExtension = ".jpg";
            else if (contentType.contains("iamge/png"))
                originalFileExtension = ".png";
            else {
                System.out.println("contentType is not vaild");
                return "";

            }
        }

        String newFileName = UUID.randomUUID().toString() + "__" + multipartFile.getOriginalFilename();

        file = new File(absolutePath + path + File.separator + newFileName);
        multipartFile.transferTo(file);

        file.setWritable(true);
        file.setReadable(true);

        return file.getAbsolutePath();
    };
}
