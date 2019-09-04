package com.hi.weiliao.base.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(
        prefix = "wxconfig",
        ignoreUnknownFields = true
)
@Data
public class WxConfig {
    private String appId;
    private String appSecret;
}
