package com.zk.wanandroid.net.http;

/**
 * @description: 服务器错误
 * @author: zhukai
 * @date: 2019/3/7 17:17
 */
public class ServerException extends RuntimeException {

    public int code; // 错误码
    public String message; // 错误信息

    public ServerException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
