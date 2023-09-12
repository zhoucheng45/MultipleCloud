package com.benny.multiple.cloud.after.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.benny.multiple.cloud.after.JSONUtil;
import com.benny.multiple.cloud.after.entity.People;
import com.benny.multiple.cloud.after.service.IPeopleService;
import com.huawei.devspore.mas.redis.core.MultiZoneClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author benny
 * @since 2023-07-21
 */
@Slf4j
@RestController
@RequestMapping("/people")

public class PeopleController {
    @Autowired
    IPeopleService peopleService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    JSONUtil jsonUtil;
    @Autowired
    private RedissonClient redissonClient;


    @Autowired
    private MultiZoneClient client;
    @GetMapping("last")
    public Page<People> last(){
        Page<People> peoplePage = peopleService.queryLast();
        return peoplePage;
    }

    @GetMapping("tx")
    public String tx(){
        peopleService.tx();
        return "OK";
    }

    @GetMapping("redis")
    public String redis(@RequestParam("key")String key, @RequestParam(value = "value", required = false) String value){
        if(StringUtils.isNotBlank(value)){
            redisTemplate.opsForValue().set(key,value);
            return key+"="+value;
        }
        value = redisTemplate.opsForValue().get(key);
        return key+"="+value;
    }

    @GetMapping("redissonClient")
    public String redissonClient(@RequestParam("key")String key){

        Page<People> peoplePage = peopleService.queryLast();
        List<People> o = null;
        if(peoplePage != null && !CollectionUtils.isEmpty(peoplePage.getRecords())) {
            RBucket<List<People>> people = redissonClient.getBucket(key);

            people.set(peoplePage.getRecords());
            o = people.get();
        }

        return key+"="+ jsonUtil.toJson(o);
    }

    boolean close = false;

    @GetMapping("redissonLock")
    public Page<People> redissonLock(@RequestParam("lock")String lockKey, @RequestParam(value = "key") String key)throws Exception{

        RLock lock = redissonClient.getLock(lockKey);
        boolean b = lock.tryLock(4, TimeUnit.MILLISECONDS);         //不会阻塞，获取到返回成功，没获取到返回失败。
        if(b) {
            Page<People> peoplePage = peopleService.queryLast();
            RBucket<Page<People>> people = redissonClient.getBucket(key);
            people.set(peoplePage);

            Page<People> o = people.get();
            while (this.close){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            lock.unlock();
            return o;
        }else {
            log.info("没有获取到锁");
            return null;
        }
    }
    @GetMapping("close")
    public String redissonLock(){
        this.close = !this.close;
        return "close="+this.close;
    }

    @GetMapping("redissonreleaseLock")
    public String redissonreleaseLock(@RequestParam("key")String key){

        RLock lock = redissonClient.getLock(key);

        lock.unlock();    // 会阻塞，直到获取锁

        return "success:"+key;
    }

}

