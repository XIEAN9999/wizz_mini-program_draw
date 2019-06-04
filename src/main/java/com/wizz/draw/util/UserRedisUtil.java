/**
 * 
 */
package com.wizz.draw.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.wizz.draw.model.UserInfo;
import com.wizz.draw.service.UserInfoService;


/**
 * @ClassName:     UserInfoGetter
 * @Description:TODO
 * @author:    XIEAN99
 * @date:        2019年5月24日 下午11:31:24
 *
 */
@Component
public class UserRedisUtil {


    @Autowired
    public RedisTemplate<String,Object> redisTemplate; 
    @Autowired
    public UserInfoService UserInfoService;
    
    public String getOpenId(String sessid) throws Exception{
        Map<String,String> id=(Map<String, String>) redisTemplate.opsForValue().get("lott::"+sessid);         
        if(id==null||id.get("openid")==null){
            UserInfo info=UserInfoService.getInfoBySessionId(sessid);
            if(info==null||info.getOpenId()==null)
                throw new Exception("用户信息不存在或过期"+sessid);
            else return info.getOpenId();
        }
            
        return id.get("openid");
    }
    
    public Map<String,String> getInfo(String sessid) throws Exception{
        Map<String,String> info=(Map<String, String>) redisTemplate.opsForValue().get("lott_pro::"+sessid);
        if(info==null){
            UserInfo info2=UserInfoService.getInfoBySessionId(sessid);
            if(info2==null)
                throw new Exception("用户头像昵称信息不存在或过期"+sessid);
            else {
                info=new HashMap<String,String>();
                info.put("nickName", info2.getNickName());
                info.put("picUrl",info2.getPicUrl());
            }              
        }           
        return info;
    }
    
    
}
