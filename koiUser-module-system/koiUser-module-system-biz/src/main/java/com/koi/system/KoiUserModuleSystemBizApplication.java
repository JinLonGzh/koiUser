package com.koi.system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.koi.system.mapper")
public class KoiUserModuleSystemBizApplication {

    public static void main(String[] args) {
        SpringApplication.run(KoiUserModuleSystemBizApplication.class, args);
    }

}
