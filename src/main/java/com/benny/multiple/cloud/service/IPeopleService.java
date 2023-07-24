package com.benny.multiple.cloud.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.benny.multiple.cloud.entity.People;
import com.baomidou.mybatisplus.extension.service.IService;

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

}
