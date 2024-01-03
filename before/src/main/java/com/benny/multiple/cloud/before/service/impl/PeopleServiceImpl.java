package com.benny.multiple.cloud.before.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.benny.multiple.cloud.before.entity.People;
import com.benny.multiple.cloud.before.mapper.PeopleMapper;
import com.benny.multiple.cloud.before.service.IPeopleService;

import org.apache.commons.lang3.RandomStringUtils;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author benny
 * @since 2023-07-21
 */
@Service
public class PeopleServiceImpl extends ServiceImpl<PeopleMapper, People> implements IPeopleService {

    public static long number;
    @Resource
    PeopleMapper peopleMapper;
    @Override
    public Page<People> queryLast() {
        LambdaQueryWrapper<People> wrapper = new LambdaQueryWrapper<>(People.class);
        wrapper.ge(People::getAge, 30)
                .orderByDesc(People::getCreateTime);
        Page<People> page = new Page<>(1,10);
        Page<People> page1 = peopleMapper.selectPage(page, wrapper);
        return page1;
    }



    @Transactional
    @Override
    public void tx() {
        List<People> peopleList = new ArrayList<>(100);


        for (int i = 0; i < 20; i++) {
            People people = getPeople();
            this.save(people);  // 采用一条一条插入，模拟大事务。

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            boolean add = peopleList.add(people);
            System.out.println("插入第"+i+"条数据："+add);
        }
//        this.saveBatch(peopleList,peopleList.size());
    }


    @Transactional
    @Override
    public void txBatch() {
        List<People> peopleList = new ArrayList<>(100);


        for (int i = 0; i < 20; i++) {
            People people = getPeople();
            peopleList.add(people);
        }
        this.saveBatch(peopleList,peopleList.size());
    }

    private static People getPeople() {
        People people = new People();
        people.setAge((int)(Math.random()*100));
        people.setName(RandomStringUtils.random(10,true,true));

        LocalDateTime now = LocalDateTime.now();
        now.plusMinutes((int)(Math.random()*100));
        people.setCreateTime(now);
        people.setUpdateTime(now);
        return people;
    }


    public void redisLock(){
        number ++;
    }


}
