提供了如何在不使用XML的情况下将Spring Security添加到现有Spring MVC应用程序的说明

SecurityConfig和使用java配置的项目一样，整个配置和springSecurityWithJavaConfig项目不同的配置是在
MessageSecurityWebApplicationInitializer2类中，该类继承AbstractAnnotationConfigDispatcherServletInitializer
实现了三个方法，第一个方法
```
@Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{RootConfiguration.class};
    }
```
加载了RootConfiguration类
```
@Configuration
@ComponentScan
public class RootConfiguration {
}
```
注解@ComponentScan会加载该包下和该包下的子包下的所有配置，SecurityConfig类
上加了@EnableWebSecurity注解，该注解包含@configuration,所以会加载该类

在springSecurityWithJavaConfig项目中，SecurityWebApplicationInitializer类继承了AbstractSecurityWebApplicationInitializer
并实现了SecurityWebApplicationInitializer（）方法，方法用来加载SecurityConfig类