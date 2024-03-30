package com.yupi.usercenter.controller;

import com.yupi.usercenter.model.domain.*;
import com.yupi.usercenter.model.request.AdminsLoginRequest;
import com.yupi.usercenter.model.request.AdminsRegisterRequest;
import com.yupi.usercenter.service.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.yupi.usercenter.constant.adminConstant.ADMIN_LOGIN_STATE;

@RestController
@RequestMapping("/admins")
public class AdminsController {

    @Autowired
    private AdminsService adminsService;
//    @Autowired
//    private ClassesService classesService;
//    @Autowired
//    private CoursesService coursesService;
//    @Autowired
//    private StudentsService studentsService;
//    @Autowired
//    private StudentcourseService studentcourseService;
//    @Autowired
//    private TeachersService teachersService;

    @RequestMapping("/register")
    public Long register(@RequestBody AdminsRegisterRequest adminsRegisterRequest) {
        if (adminsRegisterRequest==null){
            return null;
        }
        String name = adminsRegisterRequest.getName();
        String userPassword = adminsRegisterRequest.getUserPassword();
        String checkPassword = adminsRegisterRequest.getCheckPassword();
        String contact = adminsRegisterRequest.getContact();
        if(StringUtils.isAnyBlank(name, userPassword, checkPassword, contact)){
            return null;
        }
        return adminsService.adminsRegister(name, userPassword, checkPassword, contact);
    }

    @RequestMapping("/login")
    public Admins login(@RequestBody AdminsLoginRequest adminsLoginRequest, HttpServletRequest request) {
        if (adminsLoginRequest==null){
            return null;
        }
        int admin_id = adminsLoginRequest.getAdmin_id();
        String userPassword = adminsLoginRequest.getPassword();
        if(admin_id <= 0 || StringUtils.isBlank(userPassword)){
            return null;
        }
        return adminsService.adminsLogin(admin_id, userPassword, request);
    }

    /**
     * 获取当前登录的管理员信息
     * @param request
     * @return
     */
    @GetMapping("/getAdmin")
    public Admins getAdmin(HttpServletRequest request) {
        Object object = request.getSession().getAttribute(ADMIN_LOGIN_STATE);
        Admins currentAdmin=(Admins) object;
        if (currentAdmin==null){
            return null;
        }
        Integer adminId = currentAdmin.getAdmin_id();
        Admins admins = adminsService.getById(adminId);
        return  adminsService.safetyAdmins(admins);
    }

    //查询学生信息
    @GetMapping("/getStudent")
    public List<Students> getStudent(String name) {
        //返回的数据要脱敏
        return adminsService.getSelectStudent(name);
    }

    /**
     * 查询教师信息
     */
    @GetMapping("/getTeacher")
    public List<Teachers> getTeacher(String name) {
        return adminsService.getSelectTeacher(name);
    }

    /**
     * 查询课程信息
     */
    @GetMapping("/getCourse")
    public List<Courses> getCourse(String name) {
        return adminsService.getSelectCourse(name);
    }

    /**
     * 查询班级信息
     */
    @GetMapping("/getClass")
    public List<Classes> getClass(String name) {
        return adminsService.getSelectClass(name);
    }

    /**
     * 查询学生选课信息
     */
    @GetMapping("/getStudentcourse")
    public List<Studentcourse> getStudentcourse(Integer id) {
        return adminsService.getSelectStudentcourse(id);
    }

    /**
     * 添加学生信息
     */
    @PostMapping("/addStudent")
    public boolean addStudent(@RequestBody Students students) {
        //id是自增的，不需要传递
        return adminsService.addStudent(students);
    }

    /**
     * 添加教师信息
     */
    @PostMapping("/addTeacher")
    public boolean addTeacher(@RequestBody Teachers teachers) {
        return adminsService.addTeacher(teachers);
    }

    /**
     * 添加课程信息
     */
    @PostMapping("/addCourse")
    public boolean addCourse(@RequestBody Courses courses) {
        return adminsService.addCourse(courses);
    }

    /**
     * 添加班级信息
     */
    @PostMapping("/addClass")
    public boolean addClass(@RequestBody Classes classes) {
        return adminsService.addClass(classes);
    }

    /**
     * 添加学生选课信息
     */
    @PostMapping("/addStudentcourse")
    public boolean addStudentcourse(@RequestBody Studentcourse studentcourse) {
        return adminsService.addStudentcourse(studentcourse);
    }
    //这种实体信息的传递要怎么实现呢？
    //这里的实体信息传递是通过前端传递json数据，然后后端通过@RequestBody注解接收json数据，然后转换为实体对象
    //前端传递json数据的时候，要注意json数据的格式，要和实体类的属性名一致
    //这里的实体参数要加@RequetBody注解，这样才能接收到前端传递的json数据
    //前端要怎么去封装呢
    //前端要封装json数据，然后通过ajax请求发送到后端
    //这里每个字段都必须要有值吗？还是只需要传递有值的字段就可以了
    //添加学生信息也用GetMapping吗？这里应该用PostMapping

    /**
     * 更改学生信息
     */
    @PutMapping("/updateStudent")
    public boolean updateStudent(@RequestBody Students students) {
        return adminsService.updateStudent(students);
    }
    //todo 这里的更新学生信息的接口，还没测试

}
