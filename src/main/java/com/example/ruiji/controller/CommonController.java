package com.example.ruiji.controller;

import com.example.ruiji.Common.R;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.Multipart;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/common")
public class CommonController {

    @Value("${ruiji.path}")
    private String basePath;

    /**
     * 文件上传
     *
     * @param file
     * @return
     */
    @PostMapping("upload")
    public R<String> upload(MultipartFile file) {

        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String uuidName = UUID.randomUUID().toString() + suffix;

        File dir = new File(basePath);
        if (!dir.exists())
            dir.mkdirs();
        try {
            file.transferTo(new File(basePath + uuidName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return R.success(uuidName);
    }

    @GetMapping("download")
    public void download(String name, HttpServletResponse response) {

        try {
            FileInputStream inputStream = new FileInputStream(new File(basePath + name));
            ServletOutputStream outputStream = response.getOutputStream();
            response.setContentType("image/jpeg");
            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
                outputStream.flush();
            }
            inputStream.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ;
    }
}
