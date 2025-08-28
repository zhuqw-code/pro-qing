package com.zqw;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootProApplicationTests {

    @Test
    void getPath() {
        System.out.println(System.getProperty("user.dir"));
    }
    @Test
    void getSuffer() {
        String imgPath = "xxx._xxx.jpg";
        String suffer = imgPath.substring(imgPath.lastIndexOf("."));
        System.out.println(suffer);
    }
}
