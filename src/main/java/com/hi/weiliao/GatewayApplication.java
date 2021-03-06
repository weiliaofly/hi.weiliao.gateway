package com.hi.weiliao;

import com.hi.weiliao.base.config.AliConfig;
import com.hi.weiliao.base.config.QqConfig;
import com.hi.weiliao.base.config.WxConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@EnableConfigurationProperties({WxConfig.class, AliConfig.class, QqConfig.class})
@ComponentScan(basePackages = "com.hi.weiliao.**")
@MapperScan("com.hi.weiliao.**.**.mapper")
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
