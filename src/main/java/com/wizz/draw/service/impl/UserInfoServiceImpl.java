/**
 * 
 */
package com.wizz.draw.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wizz.draw.dao.UserInfoDao;
import com.wizz.draw.model.UserInfo;
import com.wizz.draw.service.UserInfoService;

/**
 * @ClassName:     UserInfoServiceImpl
 * @Description:TODO
 * @author:    XIEAN99
 * @date:        2019年6月1日 下午10:07:54
 *
 */
@Service
public class UserInfoServiceImpl implements UserInfoService{

    @Autowired
    private UserInfoDao userInfoDao;
    @Override
    public void insert(String openId, String sessionId) {
        UserInfo info=userInfoDao.getInfoByOpenId(openId);
        if(info==null){
            System.out.println("用户信息在mysql中不存在");
            userInfoDao.insert(openId, sessionId);
        }else{
            System.out.println("已储存信息用户："+info.getOpenId()+"old sess"+info.getSessionId());
            userInfoDao.updateSession(sessionId, openId);
        }
            
        
    }

    @Override
    public void insertProfileInfo(String sess,String nickName, String picUrl) {
        userInfoDao.updateInfo(nickName, picUrl, sess);      
    }

    @Override
    public UserInfo getInfoBySessionId(String sess) {
        return userInfoDao.getInfoByOpenId(sess);        
    }

  
}
