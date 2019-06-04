/**
 * 
 */
package com.wizz.draw.service;

import com.wizz.draw.model.UserInfo;

/**
 * @ClassName:     WeiXinInfoDao
 * @Description:TODO
 * @author:    XIEAN99
 * @date:        2019年6月1日 下午9:58:18
 *
 */
public interface UserInfoService {

    public void insert(String openId,String sessionId);
    public void insertProfileInfo(String sess,String nickName,String picUrl);
    public UserInfo getInfoBySessionId(String sess);
}
