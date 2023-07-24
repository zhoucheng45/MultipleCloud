package com.benny.multiple.cloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.benny.multiple.cloud.entity.People;
import com.benny.multiple.cloud.mapper.PeopleMapper;
import com.benny.multiple.cloud.service.IPeopleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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

    @Resource
    PeopleMapper peopleMapper;
    @Override
    public Page<People> queryLast() {
        LambdaQueryWrapper<People> wrapper = new LambdaQueryWrapper<>(People.class);
        wrapper.ge(People::getAge, 30);
        Page<People> page = new Page<>(1,10);
        Page<People> page1 = peopleMapper.selectPage(page, wrapper);
        return page1;
    }
}
