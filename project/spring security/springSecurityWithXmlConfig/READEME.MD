演示如何使用基于xml的配置将Spring Security与现有应用程序集成。
###1、创建spring Security配置
src/main/webapp/WEB-INF/spring/security.xml
```
<b:beans xmlns="http://www.springframework.org/schema/security"
		 xmlns:b="http://www.springframework.org/schema/beans"
		 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
		 xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
						http://www.springframework.org/schema/security http://www.springframework.org/schema/security/spring-security.xsd">

	<http />

	<user-service>
		<user name="user" password="password" authorities="ROLE_USER" />
	</user-service>

</b:beans>
```
**security.xml的作用**
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
###2、配置web.xml
src/main/webapp/WEB-INF/web.xml
```
<?xml version="1.0" encoding="UTF-8"?>
<web-app version="3.0" xmlns="http://java.sun.com/xml/ns/javaee"
		 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
		 xsi:schemaLocation="http://java.sun.com/xml/ns/javaee
  http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd">

	<!--
	  - Location of the XML file that defines the root application context
	  - Applied by ContextLoaderListener.
	  -->
	<context-param>
		<param-name>contextConfigLocation</param-name>
		<param-value>
			/WEB-INF/spring/*.xml
		</param-value>
	</context-param>


	<filter>
		<filter-name>springSecurityFilterChain</filter-name>
		<filter-class>org.springframework.web.filter.DelegatingFilterProxy</filter-class>
	</filter>
	<filter-mapping>
		<filter-name>springSecurityFilterChain</filter-name>
		<url-pattern>/*</url-pattern>
	</filter-mapping>

	<!--
	  - Loads the root application context of this web app at startup.
	  - The application context is then available via
	  - WebApplicationContextUtils.getWebApplicationContext(servletContext).
	-->
	<listener>
		<listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
	</listener>

</web-app>

```
作用：
为应用程序中的每个URL注册springSecurityFilterChain过滤器。
添加ContextLoaderListener来加载security.xml。
###3、登陆异常解决
异常信息：java.lang.IllegalArgumentException: There is no PasswordEncoder mapped for the id “null”

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
**应用自定义编辑器：**
在security.xml中加入如下bean
```
<b:bean id="passwordEncoder" class="org.springframework.security.samples.passwordEncoder.MyPasswordEncoder"></b:bean>
```