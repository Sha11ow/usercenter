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
 * @TableName classes
 */
@TableName(value ="classes")
@Data
public class Classes implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer class_id;

    /**
     * 
     */
    private String class_name;

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