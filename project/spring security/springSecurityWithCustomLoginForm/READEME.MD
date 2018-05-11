自定义登陆表单
###1、默认配置(HttpSecurity)
```
protected void configure(HttpSecurity http) throws Exception {
    http
        .authorizeRequests()
        //每个请求都要求用户进行身份验证
            .anyRequest().authenticated() 
            .and()
        //支持基于表单的身份验证
        .formLogin()                      
            .and()
        //支持HTTP基本身份验证
        .httpBasic();                     
}
```
在配置用户自定义登陆页时
```
protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll();
    }
```
其中.permitAll()需要加，否则运行项目后会显示
```
该网页无法正常运作
localhost 将您重定向的次数过多。
尝试清除 Cookie.
ERR_TOO_MANY_REDIRECTS
```
原因是在我们发起请求后，执行了以下操作：
```
   1. We make a request to our web application

   2. Spring Security sees that we are not authenticated

   3. We are redirected to /login

   4. The browser requests /login

   5. Spring Security sees that we are not authenticated

   6. We are redirected to /login …​
```
###2、Configuring a login view controller（配置一个登陆视图控制器）
org.springframework.security.samples.mvc.WebMvcConfiguration
```
@EnableWebMvc
@ComponentScan("org.springframework.security.samples.mvc")
public class WebMvcConfiguration implements WebMvcConfigurer {

    // ...

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //第一个/index表示请求路径，第二个/index表示页面路径
        registry.addViewController("/login").setViewName("login");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }
}
```
###3、创建登陆视图
src/main/resources/views/login.html
```
<html xmlns:th="http://www.thymeleaf.org">
  <head th:include="layout :: head(title=~{::title},links=~{})">
    <title>Please Login</title>
  </head>
  <body th:include="layout :: body" th:with="content=~{::content}">
    <div th:fragment="content">
        <form name="f" th:action="@{/login}" method="post">               
            <fieldset>
                <legend>Please Login</legend>
                <div th:if="${param.error}" class="alert alert-error">    
                    Invalid username and password.
                </div>
                <div th:if="${param.logout}" class="alert alert-success"> 
                    You have been logged out.
                </div>
                <label for="username">Username</label>
                <input type="text" id="username" name="username"/>        
                <label for="password">Password</label>
                <input type="password" id="password" name="password"/>    
                <div class="form-actions">
                    <button type="submit" class="btn">Log in</button>
                </div>
            </fieldset>
        </form>
    </div>
  </body>
</html>


```

4、启动项目时报NoClassDefFoundError异常
_java.lang.NoClassDefFoundError: org/springframework/web/server/WebFilter
这个异常找了我半天，网上百度各种情况，这里我出现的情况是：
我在根容器RootConfiguration类上的扫描注解范围org.springframework.security.samples，
在springMvc容器WebMvcConfiguration类上的扫描注解范围为org.springframework
这样俩个容器扫描范围出现交集，把他们分别改为org.springframework.security.samples.config
和org.springframework.security.samples.controller就行_

以上斜字体的想法是错误的，真正的原因在于我加入的spring Security的依赖包的版本为5.0.5.RELEASE
而spring加入的依赖包为4.0.1,我把他们两的版本改为一致，项目就能正常启动运行

###5、WebMvcConfigutation必须重写configureDefaultServletHandling，将静态资源设置为可用
configureDefaultServletHandling
```
@Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable(); //配置静态文件处理
    }
```

###6、几个方法的作用
- 1、addResourceHandlers(ResourceHandlerRegistry registry)
```
//重写该方法获取静态资源
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //addResourceLocations指的是文件放置的目录，addResourceHandler指的是对外暴露的访问路径
        registry.addResourceHandler("/asserts/**").addResourceLocations("classpath:/asserts/");
    }
```
当你请求http://localhost:8083/asserts/1.png时，会把classpath下的/asserts/1.png返回。注意：这里的静态资源是放置在会把classpath下的。

- 2、configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer)
```
@Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
```

此时会注册一个默认的Handler：DefaultServletHttpRequestHandler，这个Handler也是用来处理静态文件的，它会尝试映射/*。当DispatcherServelt
映射/时（/ 和/* 是有区别的），并且没有找到合适的Handler来处理请求时，就会交给DefaultServletHttpRequestHandler 来处理。注意：这里的静态资
源是放置在web根目录下，而非WEB-INF 下。 可能这里的描述有点不好懂（我自己也这么觉得），所以简单举个例子，例如：在webroot目录下有一个图片：1.png 
我们知道Servelt规范中web根目录（webroot）下的文件可以直接访问的，但是由于DispatcherServlet配置了映射路径是：/ ，它几乎把所有的请求都拦截了，从
而导致1.png 访问不到，这时注册一个DefaultServletHttpRequestHandler 就可以解决这个问题。其实可以理解为DispatcherServlet破坏了Servlet的一个特性
（根目录下的文件可以直接访问），DefaultServletHttpRequestHandler是帮助回归这个特性的。

**题外话：**

/和/*　有什么区别？
/会拦截除了jsp以外的所有url，/* 会拦截所有url，包括jsp。例如：在webroot下面有一个test.jsp,当DispatcherServlet 配置映射/ 时，浏览器输入：
http://localhost:8083/test.jsp 这个jsp是可以直接访问的，并且不经过DispatcherServlet ，而当DispatcherServlet 配置映射/* 时，这个请求就会被
DispatcherServlet 拦截。

在本项目中，DispatcherServlet配置的是/