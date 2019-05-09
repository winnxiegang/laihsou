package com.cherishTang.laishou.util.params;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by CherishTang on 2019/2/18.
 * 处理大批量数据传递，intent间
 * 使用方法：
 * 这样在启动activity之前：DataHolder.getInstance().save(someId, someObject);
 * 新的activity中获取数据：DataHolder.getInstance().retrieve(someId);
 */

public class DataHolder {
    private static DataHolder holder;

    public static DataHolder getInstance() {
        if (holder == null)
            holder = new DataHolder();
        return holder;
    }

    Map<String, WeakReference<Object>> data = new HashMap<>();

    public void save(String id, Object object) {
        data.put(id, new WeakReference<Object>(object));
    }

    public Object retrieve(String id) {
        WeakReference<Object> objectWeakReference = data.get(id);
        return objectWeakReference.get();
    }
}
