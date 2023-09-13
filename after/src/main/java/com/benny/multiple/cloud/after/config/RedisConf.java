package com.benny.multiple.cloud.after.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.huawei.devspore.mas.redis.config.MasRedisConfiguration;
import com.huawei.devspore.mas.redis.core.MultiZoneClient;
import com.huawei.devspore.mas.redis.spring.boot.cache.DcsRedisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisConf {


    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        JavaTimeModule javaTimeModule = new JavaTimeModule();   // java的时间类型
        objectMapper.registerModule(javaTimeModule);        // 可以处理java中的各种时间数据类型

        return objectMapper;
    }

    /**
     * 装配RedissonClient 客户端
     * @param objectMapper
     * @param client
     * @param masRedisConfiguration
     * @return
     */
    @Bean
    public RedissonClient redissonClient(ObjectMapper objectMapper, MultiZoneClient client, MasRedisConfiguration masRedisConfiguration) {
        Config config = new Config();
        config.setCodec(new JsonJacksonCodec(objectMapper));    // 设置序列化方式。 需要与项目中原有的序列化方式一致。 org.redisson.client.codec.Codec 接口
        return DcsRedisson.create(client,masRedisConfiguration,config);
    }
}
