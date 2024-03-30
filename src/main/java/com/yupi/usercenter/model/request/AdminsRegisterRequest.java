package com.yupi.usercenter.model.request;


import lombok.Data;

import java.io.Serializable;

@Data
public class AdminsRegisterRequest implements Serializable {

        private static final long serialVersionUID = 1L;

        private String name;

        private String userPassword;

        private String checkPassword;

        private String contact;
}
