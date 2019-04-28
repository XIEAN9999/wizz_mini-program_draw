package com.wizz.draw.util;


public class ResultData {
    private Integer status;// 状态码
    private String msg = "";// 提示信息
    private Object data;// 返回数据

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
