package org.springframework.security.samples.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.stereotype.Controller;

/**
 * @Author:longming
 * @Description:
 * @Date:Created in 19:50 2018/5/9
 * @Modified By:
 */
@Controller
@EnableWebSecurity
@ComponentScan("org")
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                //和/css/**和/index匹配的请求可以访问
                .antMatchers("/css/**", "/index").permitAll()
                //与/user/**匹配的请求需要经过身份验证，并且必须与用户角色相关联。
                .antMatchers("/user/**").hasRole("USER")
                .and()
                .formLogin()
                //基于表单的身份验证可以使用自定义登录页面和故障url。
                .loginPage("/login").failureUrl("/login-error");
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("user").password("password").roles("USER");
    }
}
