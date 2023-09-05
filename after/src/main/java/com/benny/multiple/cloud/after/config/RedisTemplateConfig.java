package com.benny.multiple.cloud.after.config;

import com.huawei.devspore.mas.redis.core.MultiZoneClient;
import com.huawei.devspore.mas.redis.spring.boot.cache.DcsConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * 对redisTemplate的改造
 */
@Slf4j
@Configuration
public class RedisTemplateConfig {
    @Bean
    public DcsConnectionFactory dcsConnectionFactory(MultiZoneClient client) {
        return new DcsConnectionFactory(client);
    }

    @Bean
    @ConditionalOnSingleCandidate(RedisConnectionFactory.class)
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        String name = redisConnectionFactory.getClass().getName();
        log.info("RedisTemplate 使用的RedisConnectionFactory:"+name);
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    @Bean
    @ConditionalOnSingleCandidate(RedisConnectionFactory.class)
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        String name = redisConnectionFactory.getClass().getName();
        log.info("StringRedisTemplate 使用的RedisConnectionFactory:"+name);
        return new StringRedisTemplate(redisConnectionFactory);
    }

}