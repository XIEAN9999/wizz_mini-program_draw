/**
 * 
 */
package com.wizz.draw;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


/**
 * @ClassName:     DrawApplication
 * @Description:TODO(这里用一句话描述这个类的作用)
 * @author:    Android_Robot
 * @date:        2019年4月28日 下午4:18:31
 *
 */

@SpringBootApplication
@MapperScan("com.wizz.draw.dao")
@ComponentScan(basePackages = {})
   
          //, ,"com.weizhi.gameMatching.util" "com.weizhi.gameMatching.web",
public class DrawApplication {

    public static void main(String[] args) {
        SpringApplication.run(DrawApplication.class, args);
    }

}
