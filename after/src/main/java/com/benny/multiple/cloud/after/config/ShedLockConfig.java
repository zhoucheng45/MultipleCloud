package com.benny.multiple.cloud.after.config;

import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.redis.spring.RedisLockProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class ShedLockConfig {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Bean
    public LockProvider lockProvider() {
        return new RedisLockProvider(stringRedisTemplate.getConnectionFactory());
    }
}