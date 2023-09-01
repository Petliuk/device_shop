package com.device.shop;

import com.device.shop.configuration.WebSecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Import;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@Import(WebSecurityConfig.class)
public class MyProjectWithSpring2Application {

    public static void main(String[] args) {
        SpringApplication.run(MyProjectWithSpring2Application.class, args);
    }

}