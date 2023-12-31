package com.benny.multiple.cloud.before.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.benny.multiple.cloud.before.api.IPeopleController;
import com.benny.multiple.cloud.before.entity.People;
import com.benny.multiple.cloud.before.service.IPeopleService;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author benny
 * @since 2023-07-21
 */
@RestController
@RefreshScope
@RequestMapping("/people")
public class PeopleController implements IPeopleController {
    @Autowired
    IPeopleService peopleService;

    @Autowired
    private StringRedisTemplate redisTemplate;


    @Autowired
    private RedissonClient redissonClient;

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
    public String redissonClient(@RequestParam("key")String key, @RequestParam(value = "value", required = false) String value){
        RBucket<String> name = redissonClient.getBucket(key);
        if(StringUtils.isNotBlank(value)){
            name.set(value);
            return key+"="+name.get();
        }
        value = name.get();

        Page<People> peoplePage = peopleService.queryLast();
        RBucket<Page<People>> people = redissonClient.getBucket("people");
        people.set(peoplePage);

        Page<People> o = people.get();

        return key+"="+value;
    }
    @GetMapping("redissonClient1")
    public Page<People> redissonClient1(@RequestParam("key")String key, @RequestParam(value = "value", required = false) String value){

        Page<People> peoplePage = peopleService.queryLast();
        RBucket<Page<People>> people = redissonClient.getBucket("people");

        people.set(peoplePage);

        Page<People> o = people.get();
        return o;
    }
    @GetMapping("redissonLock")
    public Page<People> redissonLock(@RequestParam("key")String key, @RequestParam(value = "value", required = false) String value){

        RLock lock = redissonClient.getLock(key);
        boolean b = lock.tryLock();         //不会阻塞，获取到返回成功，没获取到返回失败。

        lock.lock();    // 会阻塞，直到获取锁

        Page<People> peoplePage = peopleService.queryLast();
        RBucket<Page<People>> people = redissonClient.getBucket("people");
        people.set(peoplePage);

        Page<People> o = people.get();
        lock.unlock();
        return o;
    }

}

