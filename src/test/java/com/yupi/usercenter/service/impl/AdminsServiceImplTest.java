package com.yupi.usercenter.service.impl;

import com.yupi.usercenter.model.domain.Admins;
import com.yupi.usercenter.service.AdminsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

@SpringBootTest
public class AdminsServiceImplTest {

    @Autowired
    private AdminsService adminsService;

    @Test
    public void testAdminsService() {
        Admins admins = new Admins();
        admins.setName("admin");
        admins.setAdmin_id(1000000);
        admins.setPassword("123456");
        admins.setContact("123456789");
        boolean result = adminsService.save(admins);
        System.out.println(result);
        Assertions.assertTrue(result);
    }

    //加密测试
    @Test
    public void testDigest() {
        String password="123456";
        //对密码加密
        String userPassword = DigestUtils.md5DigestAsHex(password.getBytes());
        System.out.println(userPassword);
    }

    //管理员注册测试
    @Test
    public void testAdminsRegister() {
        long adminId = adminsService.adminsRegister("adminTest", "12345678", "12345678", "13795721365");
        System.out.println(adminId);
        Assertions.assertTrue(adminId > 0);
    }
}