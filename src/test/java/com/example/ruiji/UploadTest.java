package com.example.ruiji;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UploadTest {

    @Test
    public void test1(){
    String filename="jienijieni.jpg";
    String suffix=filename.substring(filename.lastIndexOf("."));
    System.out.println(suffix);
    }
}
