package com.benny.multiple.cloud.portal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Hello world!
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.benny.multiple.cloud.before.api")
public class Portal {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(Portal.class, args);
    }
}
