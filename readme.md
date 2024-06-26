# Spring MVC环境搭建
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


# Spring MVC注解
## @RequestMapping
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
### value属性
1. RequestMapping可以作用在类上，也可以作用在方法上.<br>
    类上的RequestMapping会增加前缀，hello方法的请求路径为"/a/b",test方法的请求路径为"/a/c"  <br>  <br>

2. value属性是一个String[]数组,可以指定多个请求路径。<br>
当只有一个请求路径时中括号可以省略。<br><br>

3. 模糊匹配,PathPattern [Doc](https://docs.spring.io/spring-framework/reference/web/webmvc/mvc-controller/ann-requestmapping.html)<br>
 ?：匹配一个字符(除/，?以外，并且不能为空)<br>
 *：匹配0个或任意字符(除/，?以外)<br>
 **： spring6版本，匹配0个或任意字符直到路径结束,左边只能是/<br>
   @RequestMapping(value = "/xyz/**")<br><br>

4. value属性的占位符,路径参数<br>
当value的值是下面这样，可以使用@PathVariable来取得中括号位置的值
```java
@RequestMapping(value = "/testValue/{username}/{password}")
public  String testValue3(@PathVariable("username") String username, @PathVariable("password") String password){
        System.out.println("username = " + username + ", password = " + password);
        return "ok";
        }
```



### method 属性
衍生的RequestMapping<br>
    @GetMapping <br>
    @PostMapping <br>
    @PutMapping <br>
    @DeleteMapping <br>
    @PatchMapping <br>

> 增：Post ，删：Delete ，改：Put ，查：get，获取响应头：Head

> 使用form表单只能有get和post请求，put，delete不能用form发出，能用ajax发出。

### params属性 
1. params属性是一个String[]数组
2. params属性的4种用法

```java
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/testParams", params = {"username", "password"})
public String testParams() {
        return"ok";
}
```
3. params不匹配报HTTP400错误：Invalid request parameters.

### headers 属性
也是String[]数组，用来设置请求头映射


# SpringMVC获取请求参数数据
## 通过原生servlet api获得请求参数
方法形参使用HttpServletRequest可以获得Tomcat的Request。

使用这种方式依赖Tomcat，不方便做junit测试。

HttpSession可以取得session

## 使用@RequestParam注解获得请求参数
1. value属性

value属性的值应该跟请求参数名一致，方法参数名不需要和请求参数值一致<br>

2. require属性

require属性设定此参数是否是必须，默认值是true

3. defaultValue属性

## 使用参数自动映射
当形参和请求参数一致时，可以省略@RequstParam注解<br>
此种方式Spring6版本不再默认支持，Spring6版本需要配置'-parameters'参数
```xml
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
\conf\server.xml
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
        chain.doFilter(request,response);
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
