# 用户管理项目

### 星球使用指南

- 星球里的项目有问题直接开搜，一般能搜到一个对应答疑文档，小伙伴的答疑一般都有解答，让人不禁感慨大星球的好处。
- 鱼皮的项目是有仓库的，不会写就去拉

### 前端

- 说实话并不会前端，配环境真的很费力气，坑还多，以后有时间再补吧
- 直接拉的鱼皮仓库代码

### 后端

day1：

- 用idea来初始化spring项目
- 官方已经不支持Java8，可以换源为aliyun.start.com（原来的为spring.start.io）
- 依赖项可以在初始化处先添加一部分

day2：

- yml文件一定注意空行格式
- 缺的依赖去[mvnrepository.com]()官网去搜
- datasource的url后面要加数据库名
- 多去看官方文档

day3：

数据表的设计

**管理员字段：**

- 属性：`admin_id`（管理员ID），`username`（用户名），`password`（密码），`name`（姓名），`contact`（联系方式），createTime（创建时间），updateTime（更新时间）

**教师字段：**

- 属性：`teacher_id`（教师ID），`name`（姓名），`title`（职称），`department`（所在部门），`contact_info`（联系方式），createTime（创建时间），updateTime（更新时间）

**学生字段：**

- 属性：`student_id`（学号），`name`（姓名），`gender`（性别），`birth_date`（出生日期），`major`（专业），`hometown`（籍贯），`phone_number`（联系电话），`class_id`（班级编号），createTime（创建时间），updateTime（更新时间）

**班级：**

- 属性：`class_id`（班级编号），`class_name`（班级名称），createTime（创建时间），updateTime（更新时间）

**课程（Courses）**：

- 属性：`course_id`（课程编号），`course_name`（课程名称），`credit_hours`（学时），`credits`（学分），`semester`（开设学期），createTime（创建时间），updateTime（更新时间）

day4：

**mybatisx**：

- 自动生成domain，service，mapper和mapper.xml

先总体设计，再理清逻辑再写代码，软件工程那一套

### **注册逻辑设计：**

- 用户在前端输入名字（name）和密码、以及校验码（todo）
- 校验用户的名字（name）、密码、校验密码，是否符合要求 
- 名字只能是汉字或者纯英文
- 密码就 不小于 8 位吧
- 密码和校验密码相同
- 手机号码联系方式
- 对密码进行加密（密码千万不要直接以明文存储到数据库中）
- 如果上述都正确，生成一个8位纯数字的账号，随机且不重复。
- 向数据库插入用户数据

数据库字段修改：账号密码都改为字符串

对着逻辑敲代码就比较简单了

**虽说现在mybatis可以驼峰命名数据库了，但我诚恳建议还是下划线的来，减少bug**

### **登录功能**

#### 接口设计

接受参数：用户账户、密码

请求类型：POST

请求体：JSON 格式的数据

*请求参数很长时不建议用 get*

返回值：用户信息（ 脱敏 ）

### 登录逻辑

- 校验用户账户和密码是否合法 

  账号是8位数字

  密码就不小于 8 位

- 校验账号和密码是否输入正确，密码要和数据库中的密文密码去对比，账号也是数据库的账号 

- 用户信息脱敏，隐藏敏感信息，防止数据库中的字段泄露 

- 我们要记录用户的登录态（session），将其存到服务器上（用后端 SpringBoot 框架封装的服务器 tomcat 去记录）
  cookie 

- 返回脱敏后的用户信息

接口设计

接受参数：用户账户、密码

请求类型：POST

请求体：JSON 格式的数据

请求参数很长时不建议用 get

返回值：用户信息（ 脱敏 ）

登录逻辑

- 校验用户账户和密码是否合法 
  非空
  账户长度为8位数字
  密码就不小于 8 位
  账户不包含特殊字符
  校验密码是否输入正确，要和数据库中的密文密码去对比 
- 用户信息脱敏，隐藏敏感信息，防止数据库中的字段泄露 
- 我们要记录用户的登录态（session），将其存到服务器上（用后端 SpringBoot 框架封装的服务器 tomcat 去记录）cookie 
- 返回脱敏后的用户信息

## 控制层 Controller 封装请求

控制器注解：

```
@RestController 适用于编写 restful 风格的 api，返回值默认为 json 类型
```

校验写在哪里？

- controller 层倾向于对请求参数本身的校验，不涉及业务逻辑本身（越少越好）
- service 层是对业务逻辑的校验（有可能被 controller 之外的类调用）

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
