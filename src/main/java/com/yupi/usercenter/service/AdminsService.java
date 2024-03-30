package com.yupi.usercenter.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.yupi.usercenter.model.domain.*;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author huxing
* @description 针对表【admins】的数据库操作Service
* @createDate 2024-03-22 20:58:44
*/
public interface AdminsService extends IService<Admins> {

    /**
     *
     * @param name
     * @param userPassword
     * @param checkPassword
     * @param contact
     * @return
     */
    long adminsRegister(String name, String userPassword, String checkPassword, String contact);

    /**
     *
     * @param admin_id
     * @param password
     * @param request
     * @return
     */
    Admins adminsLogin(int admin_id, String password, HttpServletRequest request);


    //用户数据脱敏
    Admins safetyAdmins(Admins admins);

    //查询学生信息
    List<Students> getSelectStudent(String name);

    //查询教师信息
    List<Teachers> getSelectTeacher(@Param(Constants.WRAPPER)String name);

    List<Courses> getSelectCourse(@Param(Constants.WRAPPER)String name);

    List<Studentcourse> getSelectStudentcourse(@Param(Constants.WRAPPER)int id);

    List<Classes> getSelectClass(@Param(Constants.WRAPPER)String name);

    /**
     * 添加学生
     * @param students
     * @return
     */
    boolean addStudent(Students students);

    /**
     * 添加教师
     * @param teachers
     * @return
     */
    boolean addTeacher(Teachers teachers);

    /**
     * 添加课程
     * @param courses
     * @return
     */
    boolean addCourse(Courses courses);

    /**
     * 添加班级
     * @param classes
     * @return
     */
    boolean addClass(Classes classes);

    /**
     * 添加选课信息
     * @param studentcourse
     * @return
     */
    boolean addStudentcourse(Studentcourse studentcourse);

    /**
     * 更新学生信息
     * @param students
     * @return
     */
    boolean updateStudent(Students students);
}
