package com.welding.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.welding")
@SpringBootApplication
public class WeldingMachineApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeldingMachineApplication.class, args);
    }
}
