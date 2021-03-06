package com.zcc.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zcc
 * @ClassName DataSource
 * @description
 * @date 2021/4/23 13:45
 * @Version 1.0
 */

public class DataSource {
    private static Map<String, Map<String, String>> data = new HashMap<>();

    static {
        Map<String, String> data1 = new HashMap<>();
        data1.put("password", "123456");
        data1.put("role", "user");
        data1.put("permission", "view");

        Map<String, String> data2 = new HashMap<>();
        data2.put("password", "123456");
        data2.put("role", "admin");
        data2.put("permission", "view,edit");

        data.put("zcc", data1);
        data.put("zyl", data2);
    }

    public static Map<String, Map<String, String>> getData(){
        return data;
    }
}
