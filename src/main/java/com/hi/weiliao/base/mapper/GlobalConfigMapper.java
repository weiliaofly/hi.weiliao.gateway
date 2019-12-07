package com.hi.weiliao.base.mapper;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface GlobalConfigMapper {

    List<Map<String, String>> getAllConfig();
}
