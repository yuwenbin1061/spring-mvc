## Spring MVC环境搭建
**1.pom.xml添加依赖**
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

**2.建立webapp/WEB-INF文件夹和web.xml配置文件**

2.1 tomcat配置

      Context path:/springmvc，项目的根路径，
      controlle中RequstMapping指定的path前面加上/springmvc才是url
  

2.2 web.xml配置文件
```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="https://jakarta.ee/xml/ns/jakartaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="https://jakarta.ee/xml/ns/jakartaee
         https://jakarta.ee/xml/ns/jakartaee/web-app_6_0.xsd" version="6.0">
    <!-- DispatcherServlet-->
    <!-- 1.contextConfigLocation指定springmvc配置文件的路径，默认配置文件<servlet-name>-servlet.xml -->
    <!-- 2.load-on-startup:当值为0或者大于0时，表示容器在应用启动时就加载这个servlet。
            当是一个负数时或者没有指定时，则指示容器在该servlet被选择时才加载。 -->
    <servlet>
        <servlet-name>springmvc</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:springmvc.xml</param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
    </servlet>

    <servlet-mapping>
        <servlet-name>springmvc</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>
</web-app>
```

**3.建立springmvc配置文件**

配置文件为web.xml中contextConfigLocation指定的路径（springmvc.xml），放在resources目录下
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
        <property name="cacheable" value="true"/>
    </bean>
    <bean id="templateEngine"
          class="org.thymeleaf.spring6.SpringTemplateEngine">
        <property name="templateResolver" ref="templateResolver"/>
        <property name="enableSpringELCompiler" value="true"/>
    </bean>
    <bean id="thymeleafViewResolver" class="org.thymeleaf.spring6.view.ThymeleafViewResolver">
        <property name="templateEngine" ref="templateEngine" />
        <property name="order" value="1" />
        <property name="characterEncoding" value="UTF-8" />
    </bean>

</beans>
```

**3.创建controller**
```java
package com.wenbin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {
    @RequestMapping("/")
    public String hello(){
        return "hello";
    }
}
```
**4.创建视图模板**

模板文件名hello.html，放在src/webapp/WEB-INF/templates/hello.html


## Spring MVC注解
### @RequestMapping
```java
@Controller
@RequestMapping("/a")
public class HelloController {

    @RequestMapping("/b")
    public String hello(){
        return "hello";
    }

    @RequestMapping("/c")
    public String test(){
        return "hello";
    }
}

```
1. RequestMapping可以作用在类上，也可以作用在方法上.
  类上的RequestMapping会增加前缀，hello方法的请求路径为"/a/b",test方法的请求路径为"/a/c"
  <br>
2. value属性
  value属性是一个String[],可以指定多个请求路径。
  <br>
3. 模糊匹配,PathPattern [Doc](https://docs.spring.io/spring-framework/reference/web/webmvc/mvc-controller/ann-requestmapping.html)
 ?:匹配一个字符(除/，?以外，并且不能为空)
 *：匹配0个或任意字符(除/，?以外)
 **: spring6版本，匹配0个或任意字符直到路径结束，只能出现在路径的于末尾
      spring5之前的版本，匹配0个或任意字符,路径中可以出现 /，左边只能是 /
  <br>

4. **value属性的占位符**
url参数

 
