package com.fortinet.fcasb.watcher.monitor.domain;

import java.io.Serializable;

/**
 * Created by zliu on 17/3/3.
 */
public class Result<T>  implements Serializable{
    private static final long serialVersionUID = -7341770594838106857L;
    private int code;
    private String msg;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
