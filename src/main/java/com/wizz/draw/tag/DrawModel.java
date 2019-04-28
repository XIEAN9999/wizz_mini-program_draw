/**
 * 
 */
package com.wizz.draw.tag;

/**
 * @ClassName:     DrawModel
 * @Description:TODO(这里用一句话描述这个类的作用)
 * @author:    Android_Robot
 * @date:        2019年4月28日 下午4:07:41
 *
 */
public enum DrawModel {
    
    random,high_score;
    public static DrawModel parse(int i){
        return i==0?random:high_score;
    }

}
