package org.springframework.security.samples.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * @Author:longming
 * @Description:
 * @Date:Created in 18:56 2018/5/9
 * @Modified By:
 */
public class MessageSecurityWebApplicationInitializer2 extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{RootConfiguration.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[0];
    }

    @Override
    protected String[] getServletMappings() {
        return new String[0];
    }
}
