/**
 * 
 */
package com.wizz.draw.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @ClassName:     Draw
 * @Description:TODO(这里用一句话描述这个类的作用)
 * @author:    Android_Robot
 * @date:        2019年4月28日 下午3:41:08
 *
 */
public class Draw {

    private int id;
    private String iniOpenId;
    private String theme;
    private String comName;
    private String logoId;
    private String picId;
    private Date createTime;
    private int playerNum;
    private int state; //0 准备抽奖；1 正在抽奖；2已结束;3已取消
    private Award award;
    private float beginTime;
    private Date finishTime;
    public Draw(){
    }
   
    public Draw(String initiatorOpenId, String theme, String comName,String logoId,String picId,int playerNum) {
        super();
        this.iniOpenId = initiatorOpenId;
        this.theme = theme;
        this.comName = comName;
        this.logoId=logoId;
        this.picId=picId;
        this.createTime=new Date();
        this.playerNum=playerNum;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTheme() {
        return theme;
    }
    public void setTheme(String theme) {
        this.theme = theme;
    }
    public String getComName() {
        return comName;
    }
    public void setComName(String comName) {
        this.comName = comName;
    }
    public String getLogoId() {
        return logoId;
    }
    public void setLogoId(String logoID) {
        this.logoId = logoID;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getPlayerNum() {
        return playerNum;
    }
    public void setPlayerNum(int playerNum) {
        this.playerNum = playerNum;
    }
    
  

    public String getIniOpenId() {
        return iniOpenId;
    }
 

    public void setPicId(String picId) {
        this.picId = picId;
    }

    public void setIniOpenId(String initiatorOpenId) {
        this.iniOpenId = initiatorOpenId;
    }
    public String getPicId() {
        return picId;
    }

    public void setPicID(String picId) {
        this.picId = picId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Award getAward() {
        return award;
    }

    public void setAward(Award award) {
        this.award = award;
    }

    public float getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(float beginTime) {
        this.beginTime = beginTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }
    
 
   

    
}
