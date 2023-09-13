package com.benny.multiple.cloud.after.controller;

import com.huawei.devspore.mas.redis.core.MultiZoneClient;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.core.LockConfiguration;
import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.core.SchedulerLock;
import net.javacrumbs.shedlock.core.SimpleLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Slf4j
@RestController("/redis/template/")
public class RedisTemplateTestController {
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    MultiZoneClient multiZoneClient;

    final static int DURATION = 60;

    /**
     * set   字符串、 对象
     * get
     * set 超时
     * 自增/自减
     * 集合操作（交集、差集、并集）
     * scan 游标操作
     * zset  排序
     * 分布式锁操作
     */
    @GetMapping("get")
    public String get(@RequestParam(value = "key", required = false) String key) {
        key = StringUtils.hasText(key) ? key : "name";
        String s = stringRedisTemplate.opsForValue().get(key);
        return s;
    }

    @GetMapping("set")
    public String set(@RequestParam("key") String key, @RequestParam("val") String val) {
        key = StringUtils.hasText(key) ? key : "name";
        val = StringUtils.hasText(val) ? key : "bobo";
        stringRedisTemplate.opsForValue().set(key, val, Duration.ofSeconds(DURATION));
        return "success";
    }

    @GetMapping("setWithTimeout")
    public String setWithTimeout(@RequestParam("key") String key, @RequestParam("val") String val, @RequestParam(value = "secend", required = false) Integer secend) {
        key = StringUtils.hasText(key) ? key : "name";
        val = StringUtils.hasText(val) ? key : "bobo";
        secend = secend == null ? secend : 10;

        stringRedisTemplate.opsForValue().set(key, val, Duration.ofSeconds(secend));
        return "success";
    }

    // TODO inc dec 需要依靠lua脚步封装，添加过期时间。
    @GetMapping("incr")
    public String incr(@RequestParam("key") String key, @RequestParam(value = "secend", required = false) Integer secend) {
        key = StringUtils.hasText(key) ? key : "name";
        String luaScript = "local current\n" +
                "current = redis.call('incr',KEYS[1])\n" +
                "redis.call('expire',KEYS[1], ARGV[1])\n" +
                "return current";
        RedisScript<Long> redisScript = new DefaultRedisScript<>(luaScript, Long.class);
        // 执行Lua脚本
        Long execute = stringRedisTemplate.execute(redisScript, Collections.singletonList(key), secend);

        return "success: "+execute;
    }
    @GetMapping("decr")
    public String decr(@RequestParam("key") String key, @RequestParam(value = "secend", required = false) Integer secend) {
        key = StringUtils.hasText(key) ? key : "name";
        String luaScript = "local current\n" +
                "current = redis.call('decr',KEYS[1])\n" +
                "redis.call('expire',KEYS[1], ARGV[1])\n" +
                "return current";
        RedisScript<Long> redisScript = new DefaultRedisScript<>(luaScript, Long.class);
        // 执行Lua脚本
        Long execute = stringRedisTemplate.execute(redisScript, Collections.singletonList(key), secend);

        return "success: "+execute;
    }


    @GetMapping("list")
    public String list(@RequestParam("key") String key, @RequestParam("val") String val) {
        key = StringUtils.hasText(key) ? key : "name";

        Long l = stringRedisTemplate.opsForList().leftPush(key, val);

        List<String> range = stringRedisTemplate.opsForList().range(key, 0L, 10L);
        if(CollectionUtils.isEmpty(range)){
            return "success: ";
        }
        String s = range.stream().reduce((e1, e2) -> e1 + ", " + e2).get();
        return "success: "+s;
    }


    @GetMapping("map")
    public String map(@RequestParam("key") String key, @RequestParam("hashKey") String hashKey, @RequestParam("val") String val) {


        stringRedisTemplate.opsForHash().put(key,hashKey,val);
        Object o = stringRedisTemplate.opsForHash().get(key, hashKey);
        return "success: "+o;
    }

    @GetMapping("zset")
    public String zset(@RequestParam("key") String key, @RequestParam("val") String val, @RequestParam("score") Double score) {


        Boolean add = stringRedisTemplate.opsForZSet().add(key, val, score);
        Set<String> range = stringRedisTemplate.opsForZSet().range(key, 0, 10);
        // 获取分数排行前10的

        return "success:";
    }



//    @Autowired
//    LockProvider lockProvider;
//    @SchedulerLock(name = "scheduledTaskName", lockAtMostFor = DURATION)
//    @GetMapping("lock")
//    public void scheduledTask(@RequestParam("key") String key,
//                              @RequestParam(value = "secend", required = false) Integer secend) {
//        log.info("获取锁成功");
//        SimpleLock simpleLock = lockProvider.lock(new LockConfiguration(Instant.now(), key, Duration.ofSeconds(10), Duration.ofSeconds(30))).get();
//        try {
//            Thread.sleep(20 * 1000L);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//
//    }


}
