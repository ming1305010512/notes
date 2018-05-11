package org.test.spitt.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @Author:longming
 * @Description:
 * @Date:Created in 21:26 2018/5/10
 * @Modified By:
 */
@Configuration
@ComponentScan(basePackages = {"org.test.spitt.config"},
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, value = EnableWebMvc.class)})
public class RootConfig {
}
