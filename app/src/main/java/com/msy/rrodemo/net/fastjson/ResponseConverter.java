package com.msy.rrodemo.net.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.msy.rrodemo.entity.BaseRsp;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import okio.BufferedSource;
import okio.Okio;
import retrofit2.Converter;

/**
 * Created by Administrator on 2019/12/11/011.
 */

public class ResponseConverter<T> implements Converter<ResponseBody, T> {
    private final Type type;

    public ResponseConverter(Type type) {
        this.type = type;
    }

    public T convert(ResponseBody value) throws IOException {
        BufferedSource buffer = Okio.buffer(value.source());
        String s = buffer.readUtf8();
        BaseRsp baseRsp = JSON.parseObject(s, BaseRsp.class);
        if (baseRsp.getStatus() == 401){

        }
        buffer.close();
        return JSON.parseObject(s, this.type, new Feature[0]);
    }
}