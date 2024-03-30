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
 * @TableName studentcourse
 */
@TableName(value ="studentcourse")
@Data
public class Studentcourse implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    private Integer student_id;

    /**
     * 
     */
    private Integer course_id;

    /**
     * 
     */
    private String enrollment_term;

    /**
     * 
     */
    private Date create_time;

    /**
     * 
     */
    private Date update_time;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}