package com.benny.multiple.cloud.after.config;

import com.huawei.devspore.mas.redis.config.Constants;
import com.huawei.devspore.mas.redis.config.MasRedisConfiguration;
import com.huawei.devspore.mas.redis.config.RedisServerConfiguration;
import com.huawei.devspore.mas.redis.config.RedisType;
import com.huawei.devspore.mas.redis.core.MultiZoneClient;
import com.huawei.devspore.mas.redis.exception.DcsException;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * redisson client 的改造
 */
@Slf4j
//@Service
public class RedissonClientStorage {
    // dcs的客户端
    private final MultiZoneClient client;
    // dc1和dc2的RedissonClient
    private final Map<String, RedissonClient> redissonDcsMap = new HashMap<>();
    /**
     * @param client                MultiZoneClient用于获取active.
     * @param masRedisConfiguration 获取redis配置
     * @return
     */
    public RedissonClientStorage(MultiZoneClient client, MasRedisConfiguration masRedisConfiguration) {
        this.client = client;
        if (masRedisConfiguration.getRedis().getServers().containsKey(Constants.DC_1)) {
            redissonDcsMap.put(Constants.DC_1,
                create(masRedisConfiguration.getRedis().getServers().get(Constants.DC_1)));
        }
        if (masRedisConfiguration.getRedis().getServers().containsKey(Constants.DC_2)) {
            redissonDcsMap.put(Constants.DC_2,
                create(masRedisConfiguration.getRedis().getServers().get(Constants.DC_2)));
        }
    }
    private static RedissonClient create(RedisServerConfiguration configuration) {
        if (RedisType.NORMAL.equals(configuration.getType())
            || RedisType.MASTER_SLAVE.equals(configuration.getType())) {
            Config config = new Config();
            config.useSingleServer()
                .setAddress(Constants.REDISSON_URI_PREFIX.concat(configuration.getHosts()))
                .setPassword(configuration.getPassword())
                .setDatabase(configuration.getDb());
            config.setCodec(new JsonJacksonCodec());
            return Redisson.create(config);
        } else if (RedisType.CLUSTER.equals(configuration.getType())) {
            Config config = new Config();
            config.useClusterServers()
                .setPassword(configuration.getPassword())
                .setNodeAddresses(Arrays.stream(configuration.getHosts().split(","))
                    .map(Constants.REDISSON_URI_PREFIX::concat)
                    .collect(Collectors.toList()));
            config.setCodec(new JsonJacksonCodec());
            return Redisson.create(config);
        } else {
            throw new DcsException(String.format("unknown redis type %s", configuration.getType()));
        }
    }
    /**
     * @return active的RedissonClient
     */

    public RedissonClient getActiveRedisson() {
        return redissonDcsMap.get(client.getStrategyMode().getState().getActive());
    }
    // redisson lock使用的demo
    public void lock(String key, Duration duration) throws InterruptedException {
        RedissonClient activeRedisson = this.getActiveRedisson();
        RLock lock = activeRedisson.getLock(key);
        try {
            if (lock.tryLock(duration.getSeconds(), TimeUnit.SECONDS)) {
                log.info("lock success-{}", Thread.currentThread());
                Thread.sleep(30000);
            } else {
                log.info("lock fail-{}", Thread.currentThread());
            }
        } finally {
            lock.unlock();
            log.info("unlock success-{}", Thread.currentThread());
        }
    }

}