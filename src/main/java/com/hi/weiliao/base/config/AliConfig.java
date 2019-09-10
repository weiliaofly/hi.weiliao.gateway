package com.hi.weiliao.base.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(
        prefix = "aliconfig",
        ignoreUnknownFields = true
)
@Data
public class AliConfig {
    private String accessKeyId;
    private String accessKeySecret;
}
