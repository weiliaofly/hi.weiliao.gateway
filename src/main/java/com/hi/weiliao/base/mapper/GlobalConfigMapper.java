package com.hi.weiliao.base.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface GlobalConfigMapper {

    List<Map<String, String>> getAllConfig();

    String getByKey(@Param("configKey") String configKey);

    int update(@Param("configKey")String configKey,
               @Param("configValue")String configValue);
}
