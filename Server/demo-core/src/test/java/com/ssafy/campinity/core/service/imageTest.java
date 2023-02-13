package com.ssafy.campinity.core.service;

import com.ssafy.campinity.core.utils.EmptyMultiPartFile;
import com.ssafy.campinity.core.utils.ImageUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class imageTest {

    @Autowired
    ImageUtil imageUtil;

    @Test
    void imageTest() throws IOException {
        EmptyMultiPartFile emptyFile = new EmptyMultiPartFile();
        assertEquals(0, emptyFile.create().getSize());
    }
}
