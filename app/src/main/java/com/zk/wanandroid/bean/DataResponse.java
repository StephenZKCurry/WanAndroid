package com.zk.wanandroid.bean;

/**
 * @description: 返回数据实体类
 * @author: zhukai
 * @date: 2018/3/5 13:26
 */
public class DataResponse<T> {

    /**
     * "data": ...,
     * "errorCode": 0,
     * "errorMsg": ""
     */
    private int errorCode;
    private Object errorMsg;
    private T data;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public Object getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(Object errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
