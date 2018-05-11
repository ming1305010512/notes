package org.springframework.security.samples.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.samples.passwordEncoder.MyPasswordEncoder;

/**
 * @Author:longming
 * @Description:
 * @Date:Created in 10:05 2018/5/9
 * @Modified By:
 */
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
