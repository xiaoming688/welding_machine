package com.welding.web;

import com.welding.web.config.SpringContextHolder;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.scheduling.annotation.EnableScheduling;

@ServletComponentScan("com.welding")
@MapperScan("com.welding")
@EnableScheduling
@ComponentScan(value = "com.welding")
@SpringBootApplication
public class WeldingMachineApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeldingMachineApplication.class, args);
    }

    @Bean
    public SpringContextHolder springContextHolder(ApplicationContext applicationContext) {
        SpringContextHolder springContextHolder = new SpringContextHolder();
        springContextHolder.setApplicationContext(applicationContext);
        return springContextHolder;
    }
}
