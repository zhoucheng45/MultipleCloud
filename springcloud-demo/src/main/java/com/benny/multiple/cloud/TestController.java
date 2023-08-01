package com.benny.multiple.cloud;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class TestController {

    @Value("${name}")
    String name;
    @GetMapping("/test")
    public String test() {
        return "Hello, Nacos!" + name;
    }
}
