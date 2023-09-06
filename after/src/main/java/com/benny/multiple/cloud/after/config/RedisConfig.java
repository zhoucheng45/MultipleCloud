
package com.benny.multiple.cloud.after.config;

import com.huawei.devspore.mas.redis.config.MasRedisConfiguration;
import com.huawei.devspore.mas.redis.core.MultiZoneClient;
import com.huawei.devspore.mas.redis.spring.boot.cache.DcsConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

//@Configuration
public class RedisConfig {
    @Bean
    public DcsConnectionFactory dcsConnectionFactory(@Autowired(required = false) MultiZoneClient client) {
        return new DcsConnectionFactory(client);
    }
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory dcsConnectionFactory) {
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();

        // 配置序列化
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(5))   //设置缓存失效时间
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(genericJackson2JsonRedisSerializer))
                .disableCachingNullValues();

        return RedisCacheManager.builder(dcsConnectionFactory)
                .cacheDefaults(config)
                .build();
    }

    @Bean("myRedisTemplate")
    @ConditionalOnSingleCandidate(RedisConnectionFactory.class)
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory dcsConnectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(dcsConnectionFactory);
        return template;
    }

    @Bean("myStringRedisTemplate")
    @ConditionalOnSingleCandidate(RedisConnectionFactory.class)
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory dcsConnectionFactory) {
        return new StringRedisTemplate(dcsConnectionFactory);
    }

    @Bean("redissonClientStorage")
    public RedissonClientStorage redissonClientStorage(MultiZoneClient client, MasRedisConfiguration masRedisConfiguration){
        return new RedissonClientStorage(client, masRedisConfiguration);
    }
}