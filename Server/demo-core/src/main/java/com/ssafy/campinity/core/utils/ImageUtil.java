package com.ssafy.campinity.core.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private String userHome = System.getProperty("user.home");
    private String fileName = "";
    private String uuid = "";
    private boolean image;
    public String uploadImage(MultipartFile multipartFile, String table) throws IOException {

        String uploadRoot = Paths.get(System.getProperty("user.home"))
                .resolve("upload").toString();

        logger.info("uploadRoot :" + uploadRoot);
        String imagePath = "";

        if (multipartFile.isEmpty()){
            return imagePath;
        }

        String originalFileName = multipartFile.getOriginalFilename();
        fileName = originalFileName.substring(originalFileName.lastIndexOf("\\") + 1);
        this.uuid = UUID.randomUUID().toString();
        String uploadFileName = uuid + "_" + fileName;

        File saveFile = new File(uploadRoot, File.separator + uploadFileName);
        logger.info("originalFileName : " + originalFileName);
        logger.info("saveFile : " + saveFile);

        try {
            multipartFile.transferTo(saveFile);
        } catch(IOException e) {
            e.printStackTrace();
        }

        return saveFile.getAbsolutePath();
    }

    public boolean removeImage(String imagePath){

        boolean result;
        String absolutePath = new File("demo-core" + File.separator + "src" + File.separator +
                "main" + File.separator + "resources" + File.separator + "static").getAbsolutePath();

        File file = new File(absolutePath + imagePath);
        result = file.delete();
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
