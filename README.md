# 用户管理项目

### 前端

### 后端

- 用idea来初始化spring项目
- 官方已经不支持Java8，可以换源为aliyun.start.com（原来的为spring.start.io）
- 依赖项可以在初始化处先添加一部分

- yml文件一定注意空行格式
- 缺的依赖去[mvnrepository.com]()官网去搜
- datasource的url后面要加数据库名
- 多去看官方文档


数据表的设计
- id都为4位char， 创建的时候使用4位数字
- 密码都为6位char，创建的时候使用6位数字
- 密码可以选择md5加密后存储，登录的时候重新加密比对
- 表已经符合第三范式，添加属性注意不破坏第三范式
  
**管理员表(administer)：**

- 属性：`admin_id`（管理员ID），`password`（密码），`createTime`（创建时间），`updateTime`（更新时间）
- admin_id: char(4)
- password: char(6)
-primary key: admin_id

**教师表(teacher)：**

- 属性：`teacher_id`（教师ID），`name`（姓名），`password` （密码），`createTime`（创建时间），`updateTime`（更新时间）
- teacher_id: char(4)
- name: char(4)
- password: char(6)
- primary key: teacher_id

**学生表(student)：**

- 属性：`student_id`（学号），`name`（姓名），`password` （密码），`createTime`（创建时间），`updateTime`（更新时间）
- student_id: char(4)
- name: char(4)
- password: char(6)
- primary key: student_id

**课程表(course)**：

- 属性：`course_id`（课程号），`course_name`（课程名称），`credit_hours`（学时），`credits`（学分），`semester`（学期），`createTime`（创建时间），`updateTime`（更新时间）
- course_id: char(8)
- course_name: char(8)
- credit_hours: int
- credits: int
- semester: char(6)  (例如：202401)
- primary key: (course_id, semester)

**选课表(course_seleciton)**：

- 属性：`student_id`（学号），`semester`（学期），`course_id`（课程号），`teacher_id`（学分），`score`（成绩），createTime（创建时间），updateTime（更新时间）
- score: int
- primary key: (student_id, semester, course_id, teacher_id)

**mybatisx**：

- 自动生成domain，service，mapper和mapper.xml

先总体设计，再理清逻辑再写代码，软件工程那一套

### **注册与登录逻辑设计：**
- 账号注册由管理员注册
- 登录时账号id为管理员id(admin_id)/教师id(teacher_id)/学生id(student_id)
- 密码为6为纯数字id

**虽说现在mybatis可以驼峰命名数据库了，但我诚恳建议还是下划线的来，减少bug**

### **登录功能**

#### 接口设计

接受参数：用户账户、密码

请求类型：POST

请求体：JSON 格式的数据

*请求参数很长时不建议用 get*

返回值：用户信息（ 脱敏 ）

接口设计

接受参数：用户账户、密码

请求类型：POST

请求体：JSON 格式的数据

请求参数很长时不建议用 get

返回值：用户信息（ 脱敏 ）

## 控制层 Controller 封装请求

控制器注解：

```
@RestController 适用于编写 restful 风格的 api，返回值默认为 json 类型
```

如何知道是哪个用户登录了？

javaweb 这一块的知识

- 连接服务器端后，得到一个 session 状态（匿名会话），返回给前端 
- 登录成功后，得到了登录成功的 session，并且给该sessio n设置一些值（比如用户信息），返回给前端一个设置 cookie 的 ”命令“
  session => cookie 
- 前端接收到后端的命令后，设置 cookie，保存到浏览器内 
- 前端再次请求后端的时候（相同的域名），在请求头中带上cookie去请求 
- 后端拿到前端传来的 cookie，找到对应的 session 
- 后端从 session 中可以取出基于该 session 存储的变量（用户的登录信息、登录名）

登录验证用工具栏中HTTP那一行去做，看示例代码写验证例子

**sql的代码就用mybatis带的就足够了，跨区域的就注入用（如adminservice查students）。**

mybatis辜负了我的信任，自增模块居然有坑，需要在domain层要自增的字段那里手动添加一个@TableId(type = IdType.AUTO)

# 硬啃前端

因为对api的不熟悉，想实现自己的想法非常困难，但一步步来吧

首先明前一点：ant-design-pro是后台管理系统，给管理员用的

### 换首页

- 这种全局的东西一般在app.tsx中找
- 原逻辑是当前用户不正确就重定向到login，用的history.push函数实现
- 将history.push(loginPath)改为history.push(roleSelect)
- 对应的也得加上白名单了，防止注册页面和其他登录页面的重定向
