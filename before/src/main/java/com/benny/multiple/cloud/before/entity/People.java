package com.benny.multiple.cloud.before.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author benny
 * @since 2023-07-21
 */
@Data
@TableName("people")
public class People implements Serializable {


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;



    @Schema(description = "用户年龄")
    private Integer age;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}