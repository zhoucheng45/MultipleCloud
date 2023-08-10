package com.benny.multiple.cloud.after;

import com.alibaba.nacos.spring.context.annotation.config.EnableNacosConfig;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Hello world!
 */
@SpringBootApplication
@EnableNacosConfig
public class AppAfter {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(AppAfter.class, args);
    }
}
