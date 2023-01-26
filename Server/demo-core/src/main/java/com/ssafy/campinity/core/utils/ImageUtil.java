package com.ssafy.campinity.core.utils;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
public class ImageUtil {

    public String uploadImage(MultipartFile multipartFile, String table) throws IOException {

        String imagePath = "";

        if (multipartFile.isEmpty()){
            return imagePath;
        }

        String absolutePath = new File("demo-core/src/main/resources/static").getAbsolutePath() + File.separator;
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
            else if (contentType.contains("image/png"))
                originalFileExtension = ".png";
            else {
                throw new FileUploadException("해당 파일 확장자는 지원하지 않습니다.");
            }
        }

        String newFileName = UUID.randomUUID().toString() + "__" + multipartFile.getOriginalFilename();

        file = new File(absolutePath + path + File.separator + newFileName);
        multipartFile.transferTo(file);

        file.setWritable(true);
        file.setReadable(true);

        return "\\" + path + File.separator + newFileName;
    }

    public boolean removeImage(String imagePath){

        boolean result;
        String absolutePath = new File("demo-core\\src\\main\\resources\\static").getAbsolutePath();
        File file = new File(absolutePath + imagePath);
        result = file.delete();
        return result;

    }
}
