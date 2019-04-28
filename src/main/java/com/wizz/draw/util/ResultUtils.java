package com.wizz.draw.util;



public class ResultUtils {
    public static ResultData success(){
        ResultData data = new ResultData();
        data.setMsg("操作成功！");
        data.setStatus(0);
        return data;
    }
    public static ResultData success(Object d){
        ResultData data = new ResultData();
        data.setMsg("操作成功！");
        data.setStatus(0);
        data.setData(d);
        return data;
    }
    public static  ResultData error(int code,String msg){
        ResultData data = new ResultData();
        data.setMsg(msg);
        data.setStatus(code);
        return data;
    }
}
