/**
 * 
 */
package com.wizz.draw.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import net.sf.json.JSONObject;

/**
 * @ClassName:     HttpPoster
 * @Description:TODO
 * @author:    XIEAN99
 * @date:        2019年5月15日 下午10:45:08
 *
 */
@Component
@PropertySource(value="classpath:application.properties",encoding = "UTF-8")
public class HttpPoster {

    private static String webAccessTokenApi;

    public static String post(JSONObject json,String url)
    {
               
        HttpHeaders header=new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON_UTF8);
        String jsonString=null;
        if(json!=null) jsonString=json.toString();
        System.out.println("即将返回接送数据:"+jsonString);
        HttpEntity<String>entity=new HttpEntity<String>(jsonString,header);
        RestTemplate template=new RestTemplate();
        return template.postForObject(url, entity,String.class);
        
    }
    public static JSONObject mapToJSON(Map<String,Object> map){
        JSONObject json=new JSONObject();
        if(map!=null){
            Set<String> keys=map.keySet();
        for(String key:keys)
            json.put(key, map.get(key));
        }       
        return json;
    }
    
    public static Map<String,String> getUserInfo(String code){
        String url=HttpPoster.webAccessTokenApi.replaceFirst("CODE",code);
        System.out.println("获取用户openid "+url);
        String res=HttpPoster.post(null,url);
        String[] results=res.split(",");
        Map<String,String> info=new HashMap<String,String>();
        for(String t:results)
        {
            System.out.println(t);           
            if(t.contains("openid"))
                info.put("openid",t.split(":")[1].replace("\"", ""));
            else if(t.contains("session_key"))
                info.put("key",t.split(":")[1].replace("\"", ""));
        }
        return info;
    }
    
    @Value("${weixin.api.web-access-token}")
    public  void setWebAccessTokenApi(String webAccessTokenApi) {
        HttpPoster.webAccessTokenApi = webAccessTokenApi;
    }
   
}
