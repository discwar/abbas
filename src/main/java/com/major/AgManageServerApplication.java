package com.major;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

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
