package com.msy.rrodemo.entity;

/**
 * Created by Administrator on 2019/9/27/027.
 */

public class BaseRsp<T> {
    private String msg;
    private int status;
    private T data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
