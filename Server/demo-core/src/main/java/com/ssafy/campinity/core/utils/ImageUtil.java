package com.ssafy.campinity.core.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class ImageUtil {
    private static final Logger logger = LogManager.getLogger(ImageUtil.class);
    private final String uploadImagePath;
    private String fileName = "";
    private String uuid = "";
    private String childPath = "";

    public ImageUtil(@Value("${custom.path.upload-images}") String uploadImagePath) {
        this.uploadImagePath = uploadImagePath;
    }

    public String uploadImage(MultipartFile multipartFile, String table) throws IOException {

        logger.info("uploadImagePath :" + uploadImagePath);
        String imagePath = "";

        if (multipartFile.isEmpty()){
            return imagePath;
        }

        String originalFileName = multipartFile.getOriginalFilename();
        fileName = originalFileName.substring(originalFileName.lastIndexOf("\\") + 1);
        this.uuid = UUID.randomUUID().toString();
        String uploadFileName = uuid + "_" + fileName;
        File folder = new File(uploadImagePath, File.separator + table);

        childPath = File.separator + table + File.separator + uploadFileName;
        try {
            if (!folder.exists()) {
                folder.mkdirs();
            }
        } catch (SecurityException e) {
            throw new SecurityException("이미지 업로드 폴더 생성 오류");
        }
        File saveFile = null;
        try {
            saveFile = new File(uploadImagePath, childPath);
        } catch (NullPointerException e) {
            new NullPointerException("child 파일 생성 불가");
        }

        logger.info("originalFileName : " + originalFileName);
        logger.info("saveFile : " + saveFile);

        try {
            multipartFile.transferTo(saveFile);
        } catch(IOException e) {
            e.printStackTrace();
        }

        return childPath;
    }

    public boolean removeImage(String imagePath){

        boolean result;

        File file = new File(uploadImagePath + imagePath);
        result = file.delete();
        logger.info("is_deleted : " + result);
        return result;

    }

    private boolean checkImageType(File file) {
        try {
            String contentType = Files.probeContentType(file.toPath());
            return contentType.startsWith("image");
        } catch(IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}
