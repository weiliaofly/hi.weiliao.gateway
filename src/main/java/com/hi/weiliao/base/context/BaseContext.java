package com.hi.weiliao.base.context;

import java.util.HashMap;
import java.util.Map;

public class BaseContext {

    private ThreadLocal<Map<String, Object>> context;

    public BaseContext() {
        if (context == null) {
            context = new ThreadLocal();
            context.set(new HashMap());
        }
    }

    protected Object get(String key) {
        return context.get().get(key);
    }

    protected void set(String key, Object value) {
        context.get().put(key, value);
    }
}
