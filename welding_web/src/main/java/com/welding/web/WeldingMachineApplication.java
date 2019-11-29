package com.welding.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@MapperScan("com.welding")
@ComponentScan(value = "com.welding")
@SpringBootApplication
public class WeldingMachineApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeldingMachineApplication.class, args);
    }
}
