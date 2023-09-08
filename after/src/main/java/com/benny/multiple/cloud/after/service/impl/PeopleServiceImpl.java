package com.benny.multiple.cloud.after.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.benny.multiple.cloud.after.entity.People;
import com.benny.multiple.cloud.after.mapper.PeopleMapper;
import com.benny.multiple.cloud.after.service.IPeopleService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Service
public class PeopleServiceImpl extends ServiceImpl<PeopleMapper, People> implements IPeopleService {

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
        List<People> peopleList2 = new ArrayList<>(100);

        for (int i = 0; i < 20; i++) {
            People people = new People();
            people.setAge((int)(Math.random()*100));
            people.setName(RandomStringUtils.random(10,true,true));

            LocalDateTime now = LocalDateTime.now();
            now.plusMinutes((int)(Math.random()*100));
            people.setCreateTime(now);
            people.setUpdateTime(now);
            boolean save = this.save(people);// 采用一条一条插入，模拟大事务。
            log.info("插入第"+i+"条数据："+save);
        }

        for (int i = 0; i < 20; i++) {
            People people = new People();
            people.setAge((int)(Math.random()*100));
            people.setName(RandomStringUtils.random(10,true,true));

            LocalDateTime now = LocalDateTime.now();
            now.plusMinutes((int)(Math.random()*100));
            people.setCreateTime(now);
            people.setUpdateTime(now);

            peopleList2.add(people);
        }
        boolean b = this.saveBatch(peopleList2, peopleList2.size());
        log.info("批量插入:"+b);
    }
}
