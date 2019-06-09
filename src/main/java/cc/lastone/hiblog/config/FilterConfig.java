package cc.lastone.hiblog.config;

import cc.lastone.hiblog.filter.IsLoginFilter;
import cc.lastone.hiblog.filter.LoginUserFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {


    /**
     * 判断是否登陆
     */
    @Bean
    public FilterRegistrationBean isLoginFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new IsLoginFilter());
        registration.addUrlPatterns("/*");
        registration.setName("IsLoginFilter");
        registration.setOrder(1);
        return registration;
    }

    /**
     * 验证需要登陆的页面
     */
    @Bean
    public FilterRegistrationBean loginUserFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new LoginUserFilter());
        registration.addUrlPatterns("/myInfo", "/myInfo/*");
        registration.addUrlPatterns("/myArticleComment", "/myArticleComment/*");
        registration.addUrlPatterns("/myArticle", "/myArticle/*");
        registration.addUrlPatterns("/myCategory", "/myCategory/*");
        registration.addUrlPatterns("/upload", "/upload/*");
//        registration.addUrlPatterns("/*");
        registration.setName("LoginUserFilter");
        registration.setOrder(2);
        return registration;
    }
}
