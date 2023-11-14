package com.shine.dev.show;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.shine.dev.show.mapper")
public class ImageShowApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(ImageShowApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
}
