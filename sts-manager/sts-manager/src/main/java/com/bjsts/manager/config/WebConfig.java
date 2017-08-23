package com.bjsts.manager.config;

import com.bjsts.core.enums.converter.EnableDisableStatusConverter;
import com.bjsts.core.enums.converter.YesNoStatusConverter;
import com.bjsts.manager.enums.converter.customer.CustomerTypeConverter;
import com.bjsts.manager.enums.converter.invoice.InvoiceCategoryConverter;
import com.bjsts.manager.enums.converter.invoice.InvoiceStatusConverter;
import com.bjsts.manager.enums.converter.invoice.MakeOutInvoiceStatusConverter;
import com.bjsts.manager.enums.converter.produce.PlanExecuteStatusConverter;
import com.bjsts.manager.enums.converter.resource.ResourceTypeConverter;
import com.bjsts.manager.enums.converter.sale.ContractStatusConverter;
import com.bjsts.manager.enums.converter.sale.PlanStatusConverter;
import com.bjsts.manager.enums.converter.sale.PlanTypeConverter;
import com.bjsts.manager.enums.converter.sale.SourceTypeConverter;
import com.bjsts.manager.enums.converter.user.EducationTypeConverter;
import com.bjsts.manager.enums.converter.user.MaleTypeConverter;
import com.bjsts.manager.enums.converter.user.OnJobStatusConverter;
import com.bjsts.manager.enums.converter.user.PolityTypeConverter;
import com.bjsts.manager.handler.WebExceptionHandler;
import com.bjsts.manager.interceptor.DefaultInterceptor;
import com.bjsts.manager.interceptor.ModelAttributeInterceptor;
import com.bjsts.manager.interceptor.PatternMatcherInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.MultipartConfigFactory;
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

import javax.servlet.MultipartConfigElement;
import java.io.File;

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

    @Value("${file.external.url}")
    private String fileExternalUrl;

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
                .excludePathPatterns("/document/download/**")
                .excludePathPatterns("/document/upload")
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
        registry.addConverter(new PlanStatusConverter());
        registry.addConverter(new ContractStatusConverter());
        registry.addConverter(new MakeOutInvoiceStatusConverter());
        registry.addConverter(new OnJobStatusConverter());
        registry.addConverter(new InvoiceCategoryConverter());
        registry.addConverter(new InvoiceStatusConverter());
        registry.addConverter(new PlanExecuteStatusConverter());
        registry.addConverter(new CustomerTypeConverter());

        DateFormatter dateTimeFormatter = new DateFormatter("yyyy-MM-dd HH:mm:ss");
        dateTimeFormatter.setLenient(true);
        registry.addFormatter(dateTimeFormatter);

        DateFormatter dateFormatter = new DateFormatter("yyyy-MM-dd");
        dateFormatter.setLenient(true);
        registry.addFormatter(dateFormatter);
    }

    @Bean
    public WebExceptionHandler webExceptionHandler() {
        return new WebExceptionHandler();
    }

    @Bean
    MultipartConfigElement createMultipartConfigElement()
    {
        MultipartConfigFactory mcf = new MultipartConfigFactory();
        /**
         * 设置最大上传文件的大小，默认是10MB
         */
        mcf.setMaxFileSize("50MB");
        mcf.setLocation(fileExternalUrl);
        return mcf.createMultipartConfig();
    }
}
