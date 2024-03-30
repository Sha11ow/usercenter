package com.yupi.usercenter.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName students
 */
@TableName(value ="students")
@Data
public class Students implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer student_id;

    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private Object gender;

    /**
     * 
     */
    private String major;

    /**
     * 
     */
    private Integer class_id;

    /**
     * 
     */
    private Date create_time;

    /**
     * 
     */
    private Date update_time;

    /**
     * 
     */
    private Integer role;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}