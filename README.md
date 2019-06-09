# hiblog
A Blog with Spring Boot

[官网](http://hiblog.lastone.cc/)

## 安装

### 环境搭建

- 安装java
  - [安装说明](https://www.runoob.com/java/java-environment-setup.html)
- 安装Maven
  - [安装说明](https://www.runoob.com/maven/maven-setup.html)
- 安装idea
    - [官网](https://www.jetbrains.com/)
- 安装git(可以不安装，直接下载源码)
    -  git clone https://github.com/usernameLast/hiblog.git
- 修改配置文件 src/main/resources/application.properties
```
   # 创建数据库（hiblog） 或者修改成其他名称
   spring.datasource.url=jdbc:mysql://localhost:3306/hiblog?autoReconnect=true&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=CONVERT_TO_NULL&useSSL=false&serverTimezone=CTT
   # 修改数据库用户名
   spring.datasource.username=username
   # 修改数据库密码
   spring.datasource.password=password
   # 导入数据库表和数据，初始化时设置成  ALWAYS  启动后，初始化完成，设置成 never
   spring.datasource.initialization-mode=ALWAYS
```
### 目录介绍
  - main/java/cc.lastone.hiblog/config(配置filter)
  
  - main/java/cc.lastone.hiblog/cons(定义常量)
  
  - main/java/cc.lastone.hiblog/controller(控制器)
  
  - main/java/cc.lastone.hiblog/domain(实体类)
  
  - main/java/cc.lastone.hiblog/filter(过滤器)
  
  - main/java/cc.lastone.hiblog/repository(JPA)
  
  - main/java/cc.lastone.hiblog/service(service)
  
  - main/java/cc.lastone.hiblog/to(数据传输对象)
  
  - main/java/cc.lastone.hiblog/utils(工具类)
  
  - main/java/cc.lastone.hiblog/vo(表现层对象)
  
  - main/java/cc.lastone.hiblog/HiblogApplication(启动类)
  
  - main/resources/static(静态资源目录)
  
  - main/resources/templates(前台模板目录)
  
  - main/resources/application.properties(配置文件)
  
  - main/resources/data.sql(数据库基础数据)
  
  - main/resources/schema.sql(数据库表结构)

