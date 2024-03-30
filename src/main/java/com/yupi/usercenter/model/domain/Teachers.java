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
 * @TableName teachers
 */
@TableName(value ="teachers")
@Data
public class Teachers implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer teacher_id;

    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private String title;

    /**
     * 
     */
    private String department;

    /**
     * 
     */
    private String contact_info;

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