package com.ai.server;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * springboot启动类
 */
@MapperScan(value = "com.ai.server.mapper")
@SpringBootApplication
@Slf4j
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class,args);
        log.info("项目启动:http://127.0.0.1:9090/");
        log.info("Swagger UI 界面：<http://localhost:9090/swagger-ui/index.html");
    }
}
