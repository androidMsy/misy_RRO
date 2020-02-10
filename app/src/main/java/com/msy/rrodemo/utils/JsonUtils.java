package com.msy.rrodemo.utils;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by Administrator on 2019/12/20/020.
 */

public class JsonUtils {

    public static JSONObject getRegisterAndLoginJson(String uname, String pswd){
        JSONObject object = new JSONObject();
        object.put("username", uname);
        object.put("password", pswd);
        return object;
    }
}
