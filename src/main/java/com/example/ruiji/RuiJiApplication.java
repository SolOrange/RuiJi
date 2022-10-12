package com.example.ruiji;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@Slf4j
@MapperScan("com.example.ruiji.mapper")
@ServletComponentScan
@EnableTransactionManagement
public class RuiJiApplication {

    public static void main(String[] args) {
        SpringApplication.run(RuiJiApplication.class, args);
        log.info("项目运行成功");
    }

}
