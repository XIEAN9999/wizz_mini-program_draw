/**
 * 
 */
package com.wizz.draw.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping("/token")
    public String getAccessToken(){
        return AccessTokenGetter.getAccessToken();
    }
    
    @RequestMapping("/openid")
    public String getUserOpenId(@RequestParam("code")String code,HttpSession sess){
        Map<String,String> res=HttpPoster.getUserInfo(code);
        String info=res.get("openid")+"+"+res.get("key");
        System.out.println("获取到用户信息："+info);
        info=DigestUtils.md5DigestAsHex(info.getBytes());
        System.out.println("加密后："+info);
        return info;
    }
}
