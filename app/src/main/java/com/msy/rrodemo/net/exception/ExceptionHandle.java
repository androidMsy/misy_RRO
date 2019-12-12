package com.msy.rrodemo.net.exception;

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;


public class ExceptionHandle {
    public static int errCode = ErrorStatus.UNKNOWN_ERROR;
    public static String errMsg = "请求失败，请稍后重试";

    public static String handleException(Throwable e) {
        e.printStackTrace();

        if (e instanceof SocketTimeoutException) {//网络超时
//            Logger.e("TAG", "网络连接异常: " + e.getMessage());
            errMsg = "网络连接异常";
            errCode = ErrorStatus.NETWORK_ERROR;
        } else if (e instanceof ConnectException) { //均视为网络错误
//            Logger.e("TAG", "网络连接异常: " + e.message)
            errMsg = "服务器异常，请稍后再试";
            errCode = ErrorStatus.NETWORK_ERROR;
        } else if (e instanceof HttpException) {
//            Logger.e("TAG", "网络连接异常: " + e.message)
            errMsg = "服务器异常，请稍后再试";
            errCode = ErrorStatus.NETWORK_ERROR;
        } else if (e instanceof com.alibaba.fastjson.JSONException
                || e instanceof JSONException
                || e instanceof ParseException) {   //均视为解析错误
//            Logger.e("TAG", "数据解析异常: " + e.message)
            errMsg = "数据解析异常";
            errCode = ErrorStatus.SERVER_ERROR;
        } else if (e instanceof ApiException) {//服务器返回的错误信息
            errMsg = e.getCause().getMessage().toString();
            errCode = ErrorStatus.SERVER_ERROR;
        } else if (e instanceof UnknownHostException) {
//            Logger.e("TAG", "网络连接异常: " + e.message)
            errMsg = "网络连接异常";
            errCode = ErrorStatus.NETWORK_ERROR;
        } else if (e instanceof IllegalArgumentException) {
            errMsg = "参数错误";
            errCode = ErrorStatus.SERVER_ERROR;
        }
//        else if (e instanceof ErrorException) {
//            errMsg = ((ErrorException) e).errMsg;
//            errCode = ((ErrorException) e).errCode;
//        }
        else {//未知错误
//            try {
////                Logger.e("TAG", "错误: " + e.message)
//            } catch (e1:Exception){
//                Logger.e("TAG", "未知错误Debug调试 ")
//            }

            errMsg = "请求失败，请稍后重试";
            errCode = ErrorStatus.UNKNOWN_ERROR;
        }
        return errMsg;
    }
}
