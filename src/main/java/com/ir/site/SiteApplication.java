package com.ir.site;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ir.site.dao")
public class SiteApplication {

    public static void main(String[] args) {

        SpringApplication.run(SiteApplication.class, args);
    }







}
