package com.benny.multiple.cloud.after.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.benny.multiple.cloud.after.entity.People;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author benny
 * @since 2023-07-21
 */
public interface IPeopleService extends IService<People> {

    Page<People> queryLast();

    void tx();

}
