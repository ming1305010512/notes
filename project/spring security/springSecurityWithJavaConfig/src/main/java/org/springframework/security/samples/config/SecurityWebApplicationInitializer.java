package org.springframework.security.samples.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * @Author:longming
 * @Description:
 * @Date:Created in 10:26 2018/5/9
 * @Modified By:
 */
public class SecurityWebApplicationInitializer
        extends AbstractSecurityWebApplicationInitializer {

    public SecurityWebApplicationInitializer() {
        super(SecurityConfig.class);
    }
}
