package com.benny.multiple.cloud.portal.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.benny.multiple.cloud.before.api.IPeopleController;
import com.benny.multiple.cloud.before.entity.People;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
public class PeopleController {
    @Autowired
    IPeopleController peopleController;



    @GetMapping("last")
    public Page<People> last(){
        Page<People> peoplePage = peopleController.last();
        return peoplePage;
    }

    @GetMapping("tx")
    public String tx(){

        peopleController.tx();
        return "OK";
    }

}

