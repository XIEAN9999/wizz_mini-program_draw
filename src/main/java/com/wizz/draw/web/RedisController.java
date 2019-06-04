/**
 * 
 */
package com.wizz.draw.web;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName:     RedisController
 * @Description:TODO
 * @author:    XIEAN99
 * @date:        2019年5月24日 下午10:08:10
 *
 */
@RestController
@RequestMapping("/redis")
public class RedisController {

    
    @Cacheable(cacheNames="lott",key="#id")
    public Integer redisValueW(@RequestParam("id") int id){
        return 1999;
    }
    
    @RequestMapping("/put")
    @Cacheable(cacheNames="lott",key="#id")
    public Integer redisValue(@RequestParam("id") int id){
        return id;
    }
    
    @RequestMapping("/get")
    @Cacheable(cacheNames="lott",key="#id")
    public Integer getOpenId(@RequestParam("id") int id){
        return null;
    }
}
