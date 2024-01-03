package com.benny.multiple.cloud.before.api;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.benny.multiple.cloud.before.entity.People;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author benny
 * @since 2023-07-21
 */
@FeignClient(name = "before",path = "/people")
public interface IPeopleController {
    @GetMapping("last")
    Page<People> last();

    @GetMapping("tx")
    String tx();
}

