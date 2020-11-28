package com.fz.zf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.fz.zf.*")
@SpringBootApplication
public class BootZfApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootZfApplication.class, args);
    }

}
