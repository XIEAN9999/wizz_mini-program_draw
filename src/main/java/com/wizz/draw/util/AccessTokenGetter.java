/**
 * 
 */
package com.wizz.draw.util;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @ClassName:     AccessTokenGetter
 * @Description:TODO(这里用一句话描述这个类的作用)
 * @author:    Android_Robot
 * @date:        2019年1月28日 下午8:43:40
 *
 */
@Component
@PropertySource(value="classpath:application.properties",encoding = "UTF-8")
public class AccessTokenGetter implements Runnable{

    private static String accessToken;
    private Thread getter;//凭证有效时间
    @Value("${weixin.access-token}")
    private  String url;
    
    public AccessTokenGetter()
    {
        getter=new Thread(this,"accsee_token getter");
        System.out.println("线程"+getter.getName()+"已创建");
        getter.start();
    }
    @Override
    public void run() {
       RestTemplate t=new RestTemplate();
       System.out.println("从微信api拉取access_token"+url);
       while(true){
            Map map= t.getForObject(url, Map.class);
       //正常值{"access_token":"ACCESS_TOKEN","expires_in":7200}
       for(Object key:map.keySet())
           System.out.println(key.toString()+" "+map.get(key).toString());
       if(map.containsKey("access_token")){
            this.accessToken=(String) map.get("access_token");
            Long sleepTime=Long.parseLong(map.get("expires_in").toString())-5;
           
                try {
                    System.out.println("access_token已获取，线程即将睡眠"+sleepTime);
                    Thread.sleep(sleepTime*1000);
                    System.out.println("线程已被唤醒,access_token已过期");
                    
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
           
       }
      
           
       }
          
                        
    }

    public String getUrl() {
        return url;
    }

   
    public static String getAccessToken() {
            return accessToken;
      
    }
   
    
}

