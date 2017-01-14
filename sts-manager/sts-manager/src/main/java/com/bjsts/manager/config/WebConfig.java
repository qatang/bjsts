package com.bjsts.manager.config;

import com.bjsts.core.enums.converter.EnableDisableStatusConverter;
import com.bjsts.core.enums.converter.YesNoStatusConverter;
import com.bjsts.manager.enums.converter.EducationTypeConverter;
import com.bjsts.manager.enums.converter.MaleTypeConverter;
import com.bjsts.manager.enums.converter.PolityTypeConverter;
import com.bjsts.manager.enums.converter.resource.ResourceTypeConverter;
import com.bjsts.manager.enums.converter.sale.PlanTypeConverter;
import com.bjsts.manager.enums.converter.sale.SourceTypeConverter;
import com.bjsts.manager.handler.WebExceptionHandler;
import com.bjsts.manager.interceptor.DefaultInterceptor;
import com.bjsts.manager.interceptor.ModelAttributeInterceptor;
import com.bjsts.manager.interceptor.PatternMatcherInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.config.annotation.*;

/**
 * @author jinsheng
 * @since 2016-04-27 13:17
 */
@Configuration
@EnableWebMvc
@EnableSpringDataWebSupport
@Import(WebShiroConfig.class)
@ComponentScan(basePackages = "com.bjsts.manager.controller", useDefaultFilters = false, includeFilters = @ComponentScan.Filter(value = {Controller.class, ControllerAdvice.class}))
public class WebConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private DefaultInterceptor defaultInterceptor;

    @Autowired
    private PatternMatcherInterceptor patternMatcherInterceptor;

    @Autowired
    private ModelAttributeInterceptor modelAttributeInterceptor;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("/static/", "classpath:/static/");
        registry.addResourceHandler("/plugins/**").addResourceLocations("/plugins/", "classpath:/plugins/");
        registry.addResourceHandler("/favicon.ico").addResourceLocations("/", "classpath:/static/favicon.ico");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        super.addViewControllers(registry);
        registry.addViewController("/error").setViewName("error");
        registry.addViewController("/404").setViewName("404");
        registry.addViewController("/500").setViewName("500");
        registry.addViewController("/").setViewName("redirect:/dashboard");
        registry.addViewController("/success").setViewName("success");
        registry.addViewController("/unauthorized").setViewName("unauthorized");
        registry.addViewController("/welcome").setViewName("welcome");
        registry.addViewController("/default").setViewName("default");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);

        registry.addInterceptor(defaultInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/static/**")
                .excludePathPatterns("/plugins/**")
                .excludePathPatterns("/signout");

        registry.addInterceptor(patternMatcherInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/static/**")
                .excludePathPatterns("/plugins/**")
                .excludePathPatterns("/signin")
                .excludePathPatterns("/signout")
                .excludePathPatterns("/dashboard")
                .excludePathPatterns("/unauthorized")
                .excludePathPatterns("/welcome")
                .excludePathPatterns("/default")
                .excludePathPatterns("/error")
                .excludePathPatterns("/");

        registry.addInterceptor(modelAttributeInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/static/**")
                .excludePathPatterns("/plugins/**")
                .excludePathPatterns("/signin")
                .excludePathPatterns("/signout")
                .excludePathPatterns("/dashboard")
                .excludePathPatterns("/unauthorized")
                .excludePathPatterns("/welcome")
                .excludePathPatterns("/default")
                .excludePathPatterns("/error")
                .excludePathPatterns("/");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        super.addFormatters(registry);

        registry.addConverter(new EnableDisableStatusConverter());
        registry.addConverter(new YesNoStatusConverter());
        registry.addConverter(new ResourceTypeConverter());
        registry.addConverter(new MaleTypeConverter());
        registry.addConverter(new EducationTypeConverter());
        registry.addConverter(new PolityTypeConverter());
        registry.addConverter(new PlanTypeConverter());
        registry.addConverter(new SourceTypeConverter());

        DateFormatter dateFormatter = new DateFormatter("yyyy-MM-dd HH:mm:ss");
        dateFormatter.setLenient(true);
        registry.addFormatter(dateFormatter);
    }

    @Bean
    public WebExceptionHandler webExceptionHandler() {
        return new WebExceptionHandler();
    }
}
