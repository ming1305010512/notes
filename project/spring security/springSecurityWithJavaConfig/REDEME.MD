演示如何使用基于java的配置将Spring Security与现有应用程序集成。
###1、SecurityConfig的作用
   - Require authentication to every URL in your application

   - Generate a login form for you

   - Allow the user with the Username user and the Password password to authenticate with form based authentication

   - Allow the user to logout

   - CSRF attack prevention

   - Session Fixation protection

   - Security Header integration

        - HTTP Strict Transport Security for secure requests

        - X-Content-Type-Options integration

        - Cache Control (can be overridden later by your application to allow caching of your static resources)

        - X-XSS-Protection integration

        - X-Frame-Options integration to help prevent Clickjacking

   - Integrate with the following Servlet API methods

        - HttpServletRequest#getRemoteUser()

        - HttpServletRequest.html#getUserPrincipal()

        - HttpServletRequest.html#isUserInRole(java.lang.String)

        - HttpServletRequest.html#login(java.lang.String, java.lang.String)

        - HttpServletRequest.html#logout()

###2、SecurityWebApplicationInitializer作用
- 为应用程序中的每个URL自动注册springSecurityFilterChain过滤器。
- 添加加载SecurityConfig的ContextLoaderListener。

###3、登陆报异常解决办法
异常错误为：java.lang.IllegalArgumentException: There is no PasswordEncoder mapped for the id “null”

**描述：**
要求我们提供一个密码编码器

**说明** 
有些版本不要求这样子做,所以就不会有该问题的发生,
我现在用的是5.0版本,强制要求提供一个,所以我们就给一个PasswordEncoder给他.
我们也可以使用Spring自带的PasswordEncoder.为了方便起见,我们以明文形式存密码在后台,故使用自定义PasswordEncoder

**自定义PasswordEncoder**
```
import org.springframework.security.crypto.password.PasswordEncoder;

public class MyPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence arg0) {
        return arg0.toString();
    }

    @Override
    public boolean matches(CharSequence arg0, String arg1) {
        return arg1.equals(arg0.toString());
    }

}
```
**应用自定义编辑器**
```
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .passwordEncoder(new MyPasswordEncoder())//在此处应用自定义PasswordEncoder
                .withUser("user").password("password").roles("USER");
    }
}
```
```
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>标签中的值为：
<input type="hidden" name="_csrf" value="a17cc75f-258b-4cf3-a541-c0dd2b4d4b96">
```

###4、登出 
为
了防止csrf攻击，默认情况下，spring security 的java配置注销需要：
- http方法必须是post
- 必须将CSRF标记添加到请求中。您可以使用属性_csrf在ServletRequest上访问它，如上所示