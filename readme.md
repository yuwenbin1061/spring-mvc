# 第一章 Spring MVC环境搭建

## 搭建基础Spring MVC工程

### 1.基础Maven依赖添加

搭建Spring MVC环境首先要在Maven建立的空项目中添加必要的依赖

```xml

<dependencies>
    <!-- springmvc -->
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-webmvc</artifactId>
        <version>6.1.9</version>
    </dependency>

    <!-- logback -->
    <dependency>
        <groupId>ch.qos.logback</groupId>
        <artifactId>logback-classic</artifactId>
        <version>1.5.6</version>
    </dependency>

    <!-- jakarta servlet -->
    <dependency>
        <groupId>jakarta.servlet</groupId>
        <artifactId>jakarta.servlet-api</artifactId>
        <version>6.0.0</version>
        <!-- provided意味着编译时需要，打包时不需要这个依赖 -->
        <scope>provided</scope>
    </dependency>

    <!-- thymeleaf Spring6 -->
    <dependency>
        <groupId>org.thymeleaf</groupId>
        <artifactId>thymeleaf-spring6</artifactId>
        <version>3.1.2.RELEASE</version>
    </dependency>

</dependencies>
```

### 2.建立web.xml配置文件

1. 在工程目录下建立webapp/WEB-INF文件夹<br>
2. 建立web.xml文件
3. web.xml中配置DispatcherServlet

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="https://jakarta.ee/xml/ns/jakartaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="https://jakarta.ee/xml/ns/jakartaee
         https://jakarta.ee/xml/ns/jakartaee/web-app_6_0.xsd" version="6.0">
    <!-- DispatcherServlet-->
    <servlet>
        <servlet-name>springmvc</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <!-- 1.contextConfigLocation指定springmvc配置文件的路径，默认配置文件<servlet-name>-servlet.xml -->
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:springmvc.xml</param-value>
        </init-param>
        <!-- load-on-startup:
        当值为0或者大于0时，表示容器在应用启动时就加载这个servlet。
        正数的值越小，启动是加载改servlet的优先级越高
        当是一个负数时或者没有指定时，则指示容器在该servlet被选择时才加载。 -->
        <load-on-startup>1</load-on-startup>
    </servlet>

    <servlet-mapping>
        <servlet-name>springmvc</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>
</web-app>
```

### 3.建立Spring MVC配置文件

1. 在放在resources目录下建立springmvc.xml文件
2. 编辑springmvc.xml文件配置组件扫描和视图解析,使用thymeleaf作为使用模板

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="
        http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd
        http://www.springframework.org/schema/mvc
        http://www.springframework.org/schema/mvc/spring-mvc.xsd">
    <!-- 组件扫描-->
    <context:component-scan base-package="com.wenbin"/>

    <!-- 视图解析 -->
    <bean id="templateResolver"
          class="org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver">
        <property name="prefix" value="/WEB-INF/templates/"/>
        <property name="suffix" value=".html"/>
        <property name="templateMode" value="HTML"/>
        <property name="characterEncoding" value="UTF-8"/>
    </bean>
    <bean id="templateEngine"
          class="org.thymeleaf.spring6.SpringTemplateEngine">
        <property name="templateResolver" ref="templateResolver"/>
        <property name="enableSpringELCompiler" value="true"/>
    </bean>
    <bean id="thymeleafViewResolver" class="org.thymeleaf.spring6.view.ThymeleafViewResolver">
        <property name="templateEngine" ref="templateEngine"/>
        <property name="order" value="1"/>
        <property name="characterEncoding" value="UTF-8"/>
    </bean>

    <!-- 启用MVC注解 -->
    <mvc:annotation-driven/>

</beans>
```

### 3.新建Controller

* 类上写上@Controller注解

```java
package com.wenbin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {
    @RequestMapping("/")
    public String hello() {
        return "hello";
    }
}
```

### 4.创建视图模板

1. 创建templates文件，用于存放视图模板页面<br> src/webapp/WEB-INF/templates
2. 创建hello.html

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<h1>Hello! Let`s learn Spring MVC.</h1>
</body>
</html>
```

# @RequestMapping

## 作用范围

@RequestMapping可以作用于类级别，也可以作用于方法级别

## value属性

1. value属性是一个String[]数组,可以指定多个请求路径
2. value属性同path属性

### 路径匹配：AntPathMatcher

#### 1.`?`匹配一个字符

`/resources/ima?e.png`- 在一个路径段中匹配一个字符

#### 2.`*`匹配零或多个字符

`/resources/*.png`- 在一个路径段中匹配零或多个字符

#### 3.`**`匹配复数个路径段

`/resources/**` - 匹配复数个路径段

> 上述此方法无法匹配`/`和`?`字符

> `**`只能放在末尾

### 占位符和路径参数

`@RequestMapping("/user/{username}/detail")`中括号可以匹配相应位置上的路径段<br>
例如：`/user/xiaohu/detail`匹配中间的`xiaohu`

#### 1.注解：@PathVariable

取得占位符实际的值需要用到`@PathVariable`注解作用于方法形参上<br>
@PathVariable的value值对应占位符的名字,并将其的值赋给控制器方法形参

```java
@RequestMapping("/user/{username}/detail")
public String test02(@PathVariable("username") String username){
        System.out.println("username = "+username);
        return"ok";
        }
```

> 当形参名和占位符名一样，可以省略@PathVariable,Spring6实现这个功能需要配置[-parameters](#-parameters)

## method 属性

> method属性用于执行请求的方法，当请求方法不一致时会出发Http Status 405错误。

### 衍生的RequestMapping

    @GetMapping 查
    @PostMapping 增
    @PutMapping 改
    @DeleteMapping 删
    @PatchMapping

> 使用form表单只能有get和post请求，如果指定put，delete等发出的请求还是get

### HTTP请求格式

分为请求行，请求头，空白行，请求体

## params属性

params属性是一个String[]数组

### params属性的4种用法

```java
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;

// 请求参数必须包含username，password
@RequestMapping(value = "/testParams", params = {"username", "password"})
// 请求参数必须不能包含username，必须包含password
@RequestMapping(value = "/testParams", params = {"!username", "password"})
// 请求参数必须包含username并且值为admin，必须包含password
@RequestMapping(value = "/testParams", params = {"username=admin", "password"})
// 请求参数必须包含username并且值不能是admin，必须包含password
@RequestMapping(value = "/testParams", params = {"username!=admin", "password"})
public String testParams(){
        return"ok";
        }
```

> params不匹配报HTTP400错误：Invalid request parameters.

### headers 属性

也是String[]数组，用来设置请求头映射

# SpringMVC获取请求参数数据

## 通过原生servlet api获得请求参数

方法形参使用HttpServletRequest可以获得Tomcat的Request。

使用这种方式依赖Tomcat，不方便做junit测试。

HttpSession可以取得session

## 使用@RequestParam注解获得请求参数

1.value属性

value属性的值应该跟请求参数名一致，方法参数名不需要和请求参数值一致<br>

2.require属性

require属性设定此参数是否是必须，默认值是true

3.defaultValue属性

# 使用参数自动映射<span id="-parameters"></span>

当形参和请求参数一致时，可以省略@RequstParam,@PathVariable注解<br>
此种方式Spring6版本不再默认支持，Spring6版本需要配置'-parameters'参数

```xml
<!--在maven中配置-parameters参数-->
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <version>3.5.1</version>
            <configuration>
                <compilerArgs>
                    <arg>-parameters</arg>
                </compilerArgs>
            </configuration>
        </plugin>
    </plugins>
</build>
```

## 使用pojo类接受参数

controller方法的形参使用pojo类可以接受请求参数名一致的值。

## @RequestHeader,@CookieValue

# 请求参数乱码问题

> Tomcat10默认使用UTF-8

## Get方式乱码问题

Tomcat配置URIEncoding设置为UTF-8
> Tomcat10，9默认使用UTF-8
> \conf\server.xml

```xml

<Connector executor="tomcatThreadPool"
           port="8080" protocol="HTTP/1.1"
           connectionTimeout="20000"
           redirectPort="8443"
           maxParameterCount="1000"
           URIEncoding="UTF-8"
/>
```

## Post方式乱码问题

1.Tomcat解决方式
/conf/web.xml配置文件，设置

```xml

<request-character-encoding>UTF-8</request-character-encoding>
<response-character-encoding>UTF-8</response-character-encoding>
```

2.自定义过滤器

```java
package com.wenbin.filter;

import jakarta.servlet.*;

import java.io.IOException;

public class CharacterEncodingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        chain.doFilter(request, response);
    }
}
```

```xml
<!--  配置编码过滤器  -->
<filter>
    <filter-name>characterEncodingFilter</filter-name>
    <filter-class>com.wenbin.filter.CharacterEncodingFilter</filter-class>
</filter>
<filter-mapping>
<filter-name>characterEncodingFilter</filter-name>
<url-pattern>/*</url-pattern>
</filter-mapping>
```

3.使用Springmvc内置过滤器

```xml
<!--  配置编码过滤器  -->
<filter>
    <filter-name>characterEncodingFilter</filter-name>
    <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
    <init-param>
        <param-name>encoding</param-name>
        <param-value>UTF-8</param-value>
    </init-param>
    <init-param>
        <param-name>forceRequestEncoding</param-name>
        <param-value>true</param-value>
    </init-param>
    <init-param>
        <param-name>forceResponseEncoding</param-name>
        <param-value>true</param-value>
    </init-param>
</filter>
<filter-mapping>
<filter-name>characterEncodingFilter</filter-name>
<url-pattern>/*</url-pattern>
</filter-mapping>
```

# 视图

## 视图控制器

```xml

<mvc:view-controller path="/" view-name="index"/>
```

当请求不需要经过后台处理,直接返回视图的情况，可以使用springmvc配置中的view-controller。<br>
此种方法不需要写controller方法就能返回视图。

> 使用view-controller配置时，mvc的注解驱动会失效，需要开启注解驱动

```xml
<!-- 开启Spring MVC的注解驱动-->
<mvc:annotation-driven/>
```

# 静态资源访问

Spring MVC请求静态资源有两种方式<br>
1.第一种使用tomcat的默认servlet，通过springmvc配置文件中添加`<mvc:default-servlet-handler />`配置<br>

2.第二种方式使用spring的mvc:resources配置

```xml

<mvc:resources mapping="/static/**" location="/static/"/>
```

mapping属性指定请求路径，location指定静态资源目录

# 发送PUT，DELETE请求

默认的form标签只能发送GET和POST请求，想要发送PUT，DELETE请求需要使用Spring MVC的HiddenHttpMethodFilter过滤器<br>

* 使用HiddenHttpMethodFilter过滤器有几点要求:<br>

1. 页面需要是POST请求
2. 表单中有一个name为"_method"隐藏表单，并将其值设为PUT或POST（大小写不限）
3. web.xml中配置过滤器

HiddenHttpMethodFilter过滤器会根据"_method"设定的值将请求转换成PUT或DELETE方法<br>
> HiddenHttpMethodFilter过滤器徐配置在字符编码过滤器之后，否则字符编码过滤器会失效

# Spring MVC表单验证

## Bean Validation

### 基本用法

1.引入依赖

```xml

<dependency>
    <groupId>org.hibernate.validator</groupId>
    <artifactId>hibernate-validator</artifactId>
    <version>8.0.1.Final</version>
</dependency>
```

2.在要校验对bean属性上加校验注解
3.Controller方法参数使用@Valid注解

### 校验注解

1.Bean Validation注解：

| 注解                          | 说明                           |
|-----------------------------|------------------------------|
| @Valid                      | 被注释的元素是一个对象，需要检查此对象的所有字段值    |
| @Null                       | 被注释的元素必须为 null               |
| @NotNull                    | 被注释的元素必须不为 null              |
| @AssertTrue                 | 被注释的元素必须为 true               |
| @AssertFalse                | 被注释的元素必须为 false              |
| @Min(value)                 | 被注释的元素必须是一个数字，其值必须大于等于指定的最小值 |
| @Max(value)                 | 被注释的元素必须是一个数字，其值必须小于等于指定的最大值 |
| @DecimalMin(value)          | 被注释的元素必须是一个数字，其值必须大于等于指定的最小值 |
| @DecimalMax(value)          | 被注释的元素必须是一个数字，其值必须小于等于指定的最大值 |
| @Size(max, min)             | 被注释的元素的大小必须在指定的范围内           |
| @Digits (integer, fraction) | 被注释的元素必须是一个数字，其值必须在可接受的范围内   |
| @Past                       | 被注释的元素必须是一个过去的日期             |
| @Future                     | 被注释的元素必须是一个将来的日期             |
| @Pattern(value)             | 被注释的元素必须符合指定的正则表达式           |

2.Hibernate Validator的注解：

| 注解                                           | 说明                                         |
|----------------------------------------------|--------------------------------------------|
| @Email                                       | 被注释的元素必须是电子邮箱地址                            |
| @Length(min=, max=)                          | 被注释的字符串的大小必须在指定的范围内                        |
| @NotEmpty                                    | 被注释的集合的不能非空                                |
| @Range(min=, max=)                           | 被注释的元素必须在合适的范围内                            |
| @NotBlank                                    | 被注释的字符串的必须非空                               |
| @URL(protocol=,host=, port=,regexp=, flags=) | 被注释的字符串必须是一个有效的url                         |
| @CreditCardNumber                            | 被注释的字符串必须通过Luhn校验算法银行卡，信用卡等号码一般都用Luhn计算合法性 |

## 校验国际化