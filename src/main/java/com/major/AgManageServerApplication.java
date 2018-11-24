package com.major;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 项目启动类
 * @author LianGuoQing
 */
@SpringBootApplication
@EnableScheduling
public class AgManageServerApplication  {


    public static void main(String[] args) {

        SpringApplication.run(AgManageServerApplication.class, args);

    }

}
