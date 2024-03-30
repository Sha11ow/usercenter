package com.yupi.usercenter.model.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class AdminsLoginRequest implements Serializable {

    private static final long serialVersionUID = 2L;

    private int admin_id;

    private String password;

}
