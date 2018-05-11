package org.springframework.security.samples.mvc;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

import java.util.List;

/**
 * @Author:longming
 * @Description:
 * @Date:Created in 10:51 2018/5/10
 * @Modified By:
 */
@Configuration
@EnableWebMvc
@ComponentScan("org.springframework.security.samples")
public class WebMvcConfiguration implements WebMvcConfigurer{


//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/resources/**").addResourceLocations("classpath:/resources/")
//                .setCachePeriod(31556926);
//        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
//    }



    @Bean
    public ViewResolver viewResolver() { //配置JSP视图解析器
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/classes/views/");
        resolver.setSuffix(".html");
        //可以在JSP页面中通过${}访问beans
        resolver.setExposeContextBeansAsAttributes(true);
        return resolver;
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable(); //配置静态文件处理
    }
}
