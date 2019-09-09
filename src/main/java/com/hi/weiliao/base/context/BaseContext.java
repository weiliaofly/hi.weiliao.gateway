package com.hi.weiliao.base.context;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

public class BaseContext {

    private ThreadLocal<Map<String, Object>> context = new ThreadLocal();

    @Autowired
    public Map getInstans() {
        Map map = context.get();
        if(map == null){
            map = new HashMap();
            context.set(map);
        }
        return map;
    }

    protected Object get(String key) {
        return getInstans().get(key);
    }

    protected void set(String key, Object value) {
        getInstans().put(key, value);
    }
}
