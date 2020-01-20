package com.hi.weiliao.base.service.impl;

import com.hi.weiliao.base.mapper.GlobalConfigMapper;
import com.hi.weiliao.base.service.GlobalConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GlobalConfigServiceImpl implements GlobalConfigService {

    private static Map<String, String> GLOBAL_CONFIG = new HashMap();

    @Autowired
    private GlobalConfigMapper globalConfigMapper;

    @Autowired
    public GlobalConfigServiceImpl(GlobalConfigMapper globalConfigMapper) {
        if (CollectionUtils.isEmpty(GLOBAL_CONFIG)) {
            List<Map<String, String>> configs = globalConfigMapper.getAllConfig();
            for (Map<String, String> config : configs) {
                String configKey = config.get("CONFIG_KEY");
                String configValue = config.get("CONFIG_VALUE");
                GLOBAL_CONFIG.put(configKey, configValue);
            }
        }
    }

    @Override
    public String getConfigValue(String configKey) {
        String configValue = GLOBAL_CONFIG.get(configKey);
        if(StringUtils.isEmpty(configValue)) {
            configValue = globalConfigMapper.getByKey(configKey);
            GLOBAL_CONFIG.put(configKey, configValue);
        }
        return configValue;
    }

    @Override
    public int updateConfigValue(String configKey, String configValue) {
        return globalConfigMapper.update(configKey, configValue);
    }
}
