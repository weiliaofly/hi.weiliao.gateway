package com.hi.weiliao.base.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(
        prefix = "qqconfig",
        ignoreUnknownFields = true
)
@Data
public class QqConfig {
    private String appId;
    private String appSecret;
    private String appToken;
}
