/**
 * 
 */
package com.wizz.draw.model;

import java.io.Serializable;

/**
 * @ClassName:     WeiXinUserInfo
 * @Description:TODO
 * @author:    XIEAN99
 * @date:        2019年5月24日 下午5:13:11
 *
 */
public class UserInfo {
    
    private String sessionId;
    private String openId;
    private String nickName;
    private String picUrl;
    
   
    public String getNickName() {
        return nickName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public String getPicUrl() {
        return picUrl;
    }
    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
    
    public String getSessionId() {
        return sessionId;
    }
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
    
    
}
