package org.springframework.security.samples.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @Author:longming
 * @Description:
 * @Date:Created in 18:51 2018/5/9
 * @Modified By:
 */
@Configuration
@ComponentScan(basePackages = {"org.springframework.security.samples"},
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, value = EnableWebMvc.class)})
public class RootConfiguration {
}
