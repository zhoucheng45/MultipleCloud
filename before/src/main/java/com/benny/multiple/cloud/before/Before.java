package com.benny.multiple.cloud.before;

//import com.alibaba.nacos.spring.context.annotation.config.EnableNacosConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Hello world!
 */
@SpringBootApplication
@EnableDiscoveryClient
public class Before {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(Before.class, args);
    }
}
