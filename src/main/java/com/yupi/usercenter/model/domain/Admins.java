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
 * @TableName admins
 */
@TableName(value ="admins")
@Data
public class Admins implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer admin_id;

    /**
     * 
     */
    private String password;

    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private String contact;

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