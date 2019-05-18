/**
 * 
 */
package com.wizz.draw.util;

import java.io.IOException;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName:     GlobalExceptionHandler
 * @Description:TODO
 * @author:    XIEAN99
 * @date:        2019年4月28日 下午9:32:23
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value=IOException.class)
    public ResultData IOExceptionHandler(IOException e){
        ResultData res=new ResultData();
        res.setStatus(-2);       
        res.setMsg("文件操作失败");       
        res.setData(e.getMessage());
        e.printStackTrace();
        return res;
    }
    @ResponseBody
    @ExceptionHandler(value=RuntimeException.class)
    public ResultData runTimeExceptionHandler(RuntimeException e){
        ResultData res=new ResultData();
        res.setStatus(-3);       
        res.setMsg("数据库操作失败");       
        res.setData(e.getMessage());
        e.printStackTrace();
        return res;
    }
    @ResponseBody
    @ExceptionHandler(value=Exception.class)
    public ResultData generiExceptionHandler(Exception e){
        ResultData res=new ResultData();
        res.setStatus(-1);       
        res.setMsg("操作失败");       
        res.setData(e.getMessage());
        e.printStackTrace();
        return res;
    }
}
