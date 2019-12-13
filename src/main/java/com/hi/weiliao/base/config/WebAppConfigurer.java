package com.hi.weiliao.base.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.hi.weiliao.interceptor.ManagerInterceptor;
import com.hi.weiliao.interceptor.SessionInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.TimeZone;

@Configuration
public class WebAppConfigurer implements WebMvcConfigurer {

    @Bean
    public HandlerInterceptor getSessionInterceptor() {
        return new SessionInterceptor();
    }

    @Bean
    public HandlerInterceptor getManagerInterceptor() {
        return new ManagerInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 可添加多个
        registry.addInterceptor(getSessionInterceptor())
                .addPathPatterns("/userinfo/**")
                .addPathPatterns("/userauth/set_phone")
                .addPathPatterns("/userauth/set_password")
                .addPathPatterns("/userauth/change_password")
                .addPathPatterns("/userauth/sign_in")
                .addPathPatterns("/userauth/sign_history")
                .addPathPatterns("/userfollow/follow")
                .addPathPatterns("/userfollow/cancel_follow")
                .addPathPatterns("/userfollow/count");

        registry.addInterceptor(getManagerInterceptor())
                .addPathPatterns("/usermanage/**");
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        for (int i = 0; i < converters.size(); i++) {
            if (converters.get(i) instanceof MappingJackson2HttpMessageConverter) {
                ObjectMapper objectMapper = new ObjectMapper();
                // 统一返回数据的输出风格
                objectMapper.setPropertyNamingStrategy(new PropertyNamingStrategy.SnakeCaseStrategy());
                objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
                MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
                converter.setObjectMapper(objectMapper);
                converters.set(i, converter);
                break;
            }
        }
    }
}