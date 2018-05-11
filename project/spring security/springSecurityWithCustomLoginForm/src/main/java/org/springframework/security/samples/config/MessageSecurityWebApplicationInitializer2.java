package org.springframework.security.samples.config;

import org.springframework.security.samples.mvc.WebMvcConfiguration;
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
    }//根容器

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{WebMvcConfiguration.class};
    }//Spring mvc容器

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};//DispatcherServlet映射,从"/"开始
    }
}
