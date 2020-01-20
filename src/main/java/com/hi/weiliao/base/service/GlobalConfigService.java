package com.hi.weiliao.base.service;

public interface GlobalConfigService {

    String getConfigValue(String configKey);

    int updateConfigValue(String configKey, String configValue);
}
