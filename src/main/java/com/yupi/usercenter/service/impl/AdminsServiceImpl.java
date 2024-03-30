package com.yupi.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.usercenter.model.domain.*;
import com.yupi.usercenter.service.*;
import com.yupi.usercenter.mapper.AdminsMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.yupi.usercenter.constant.adminConstant.ADMIN_LOGIN_STATE;

/**
* @author huxing
* @description 针对表【admins】的数据库操作Service实现
* @createDate 2024-03-22 20:58:44
*/
@Service
@Slf4j
public class AdminsServiceImpl extends ServiceImpl<AdminsMapper, Admins>
    implements AdminsService{

    @Autowired
    private ClassesService classesService;
    @Autowired
    private CoursesService coursesService;
    @Autowired
    private StudentsService studentsService;
    @Autowired
    private StudentcourseService studentcourseService;
    @Autowired
    private TeachersService teachersService;


    private static final String SALT="yupi";
    //用户登录态键


    @Override
    public long adminsRegister(String name, String userPassword, String checkPassword, String contact) {
        if (StringUtils.isAnyBlank(name, userPassword, checkPassword)) { return -1; }
        //账户名字限制，只能全中文或者全英文，不能包含特殊字符
        if(!name.matches("^[a-zA-Z]+$") && !name.matches("^[\u4e00-\u9fa5]+$")){
            return -1;
        }

        //手机号码联系方式
        if(!contact.matches("^[1][3,4,5,7,8][0-9]{9}$")){
            return -1;
        }

        if(userPassword.length() < 8 || userPassword.length() > 20){
            return -1;
        }

        if(!userPassword.equals(checkPassword)){
            return -1;
        }

        //对密码加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT+userPassword).getBytes());

        //生成8位的用户账号（纯数字）,不重复,
        int account = (int)((Math.random()*9+1)*10000000);
        QueryWrapper<Admins> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("admin_id",account);
        Admins admins1 = this.getOne(queryWrapper);
        while(admins1 != null){
            account = (int)((Math.random()*9+1)*10000000);
            queryWrapper.eq("admin_id",account);
            admins1 = this.getOne(queryWrapper);
        }

        //插入数据
        Admins admins = new Admins();
        admins.setName(name);
        admins.setAdmin_id(account);
        admins.setPassword(encryptPassword);
        admins.setContact(contact);
        boolean result = this.save(admins);
        if(!result){
            return -1;
        }

        return admins.getAdmin_id();
    }


    @Override
    public Admins adminsLogin(int admin_id, String password, HttpServletRequest request) {
        //登录验证
        //如果账号不为8位数字，或者密码为空，返回null
        if (admin_id < 10000000 || admin_id > 99999999) { return null; }
        if (StringUtils.isAnyBlank(password)) { return null; }
        //对密码加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT+password).getBytes());
        QueryWrapper<Admins> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("admin_id",admin_id);
        queryWrapper.eq("password",encryptPassword);
        Admins admins = this.getOne(queryWrapper);
        if(admins == null){
            log.info("账号或密码错误");
            return null;
        }
        //用户数据脱敏
        Admins safetyAdmin = safetyAdmins(admins);

        //登录成功,会话保存用户信息
        request.getSession().setAttribute(ADMIN_LOGIN_STATE,safetyAdmin);

        return safetyAdmin;
    }

    //用户数据脱敏
    @Override
    public Admins safetyAdmins(Admins admins){
        Admins safetyAdmin = new Admins();
        safetyAdmin.setAdmin_id(admins.getAdmin_id());
        safetyAdmin.setName(admins.getName());
        safetyAdmin.setContact(admins.getContact());
        safetyAdmin.setCreate_time(admins.getCreate_time());
        return safetyAdmin;
    }


    //查询学生信息
    @Override
    public List<Students> getSelectStudent(String name) {
        QueryWrapper<Students> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name",name);
        return studentsService.list(queryWrapper);
    }

    //查询教师信息
    @Override
    public List<Teachers> getSelectTeacher(String name) {
        QueryWrapper<Teachers> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name",name);
        return teachersService.list(queryWrapper);
    }

    /**
     * 查询课程信息
     */
    @Override
    public List<Courses> getSelectCourse(String name) {
        QueryWrapper<Courses> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("course_name",name);
        return coursesService.list(queryWrapper);
    }

    /**
     * 查询选课信息
     */
    @Override
    public List<Studentcourse> getSelectStudentcourse(int id) {
        QueryWrapper<Studentcourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("student_id", id);
        return studentcourseService.list(queryWrapper);
    }

    /**
     * 查询班级信息
     */
    @Override
    public List<Classes> getSelectClass(String name) {
        QueryWrapper<Classes> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("class_name", name);
        return classesService.list(queryWrapper);
    }

    /**
     * 添加学生
     * @param students
     * @return
     */
    @Override
    public boolean addStudent(Students students) {
        return studentsService.save(students);
    }

    /**
     * 添加教师
     * @param teachers
     * @return
     */
    @Override
    public boolean addTeacher(Teachers teachers) {
        return teachersService.save(teachers);
    }

    /**
     * 添加课程
     * @param courses
     * @return
     */
    @Override
    public boolean addCourse(Courses courses) {
        return coursesService.save(courses);
    }

    @Override
    public boolean addClass(Classes classes) {
        return classesService.save(classes);
    }

    @Override
    public boolean addStudentcourse(Studentcourse studentcourse) {
        return studentcourseService.save(studentcourse);
    }

    /**
     * 更新学生信息
     */
    @Override
    public boolean updateStudent(Students students) {
        return studentsService.updateById(students);
    }


}




