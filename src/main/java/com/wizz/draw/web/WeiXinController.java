/**
 * 
 */
package com.wizz.draw.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wizz.draw.service.UserInfoService;
import com.wizz.draw.util.AccessTokenGetter;
import com.wizz.draw.util.HttpPoster;

/**
 * @ClassName:     WeiXinController
 * @Description:TODO
 * @author:    XIEAN99
 * @date:        2019年5月15日 下午10:10:40
 *
 */
@RestController
@RequestMapping("/weixin")
public class WeiXinController {

    @Autowired
    public RedisTemplate<String,Object> redisTemplate; 
    @Autowired
    public UserInfoService UserInfoService;
    @RequestMapping("/token")
    public String getAccessToken(){
        return AccessTokenGetter.getAccessToken();
    }
    
    @RequestMapping("/user/openid")
    public String updateUserOpenId(@RequestParam("code")String code){
        Map<String,String> res=HttpPoster.getUserInfo(code);
        String openid=res.get("openid");
        String info=res.get("openid")+"+"+openid;        
        info=DigestUtils.md5DigestAsHex(info.getBytes());
        System.out.println("获取到用户信息："+info+" openid:"+openid);
        Map<String,String> id=new HashMap<String,String>();
        id.put("openid", openid);
        redisTemplate.opsForValue().set("lott::"+info,id);
        UserInfoService.insert(openid, info);
        return info;
    }
     
    @RequestMapping("/user/info")
    public Object updateUserInfoToRedis(@RequestParam("sessionId")String id,
                                                @RequestParam(value="name")String name,
                                                @RequestParam(value="url")String url){
        Map<String,String> info=new HashMap<String,String>();
        info.put("nickName", name);
        info.put("picUrl",url);
        System.out.println("更新用户信息："+info.get("nickName")+" picurl:"+info.get("picUrl"));
        redisTemplate.opsForValue().set("lott_pro::"+id,info);
        UserInfoService.insertProfileInfo(id, name, url);
        return info;
    }
    
    
    
    
    
}
