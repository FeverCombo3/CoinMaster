package com.yjq.coinmaster.base;

import java.io.Serializable;

/**
 * @author jjx
 * @date 2018/8/3
 */

public class Result<T> implements Serializable {
//          "success": true,
//        "errorNo": 0,
//        "errorMsg": null,
//        "data":
    public static final boolean SUCCESS = true;
    public static final int FAIL = 1;

    /**
     * true：成功，false：失败
     */
    public boolean success;
    private int errorNo;

    private String errorMsg;

    private T data;


    public int getErrorNo() {
        return errorNo;
    }

    public void setErrorNo(int errorNo) {
        this.errorNo = errorNo;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
