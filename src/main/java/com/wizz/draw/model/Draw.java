/**
 * 
 */
package com.wizz.draw.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.wizz.draw.tag.DrawState;

/**
 * @ClassName:     Drae
 * @Description:TODO(这里用一句话描述这个类的作用)
 * @author:    Android_Robot
 * @date:        2019年4月28日 下午3:41:08
 *
 */
public class Draw {

    private int id;
    private String initiatorOpenId;
    private String theme;
    private String comName;
    private String logoId;
    private String picId;
    private Date createTime;
    private Date startTime;
    private int playerNum;
    private List<Award> awards;
    private List<DrawRecord> records;
    private DrawState state;
    public Draw(){
        this.records=new ArrayList<DrawRecord>();
        this.awards=new ArrayList<Award>();
    }
   
    public Draw(String initiatorOpenId, String theme, String comName) {
        super();
        this.initiatorOpenId = initiatorOpenId;
        this.theme = theme;
        this.comName = comName;
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
    public Date getStartTime() {
        return startTime;
    }
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
    public int getPlayerNum() {
        return playerNum;
    }
    public void setPlayerNum(int playerNum) {
        this.playerNum = playerNum;
    }
    
    public List<Award> getAwards() {
        return awards;
    }


    public void setAwards(List<Award> awards) {
        this.awards = awards;
    }
    

    public String getInitiatorOpenId() {
        return initiatorOpenId;
    }


    public void setInitiatorOpenId(String initiatorOpenId) {
        this.initiatorOpenId = initiatorOpenId;
    }


    public List<DrawRecord> getRecords() {
        return records;
    }
    public void setRecords(List<DrawRecord> records) {
        this.records = records;
    }
    
    public String getPicId() {
        return picId;
    }

    public void setPicID(String picId) {
        this.picId = picId;
    }

    public DrawState getState() {
        return state;
    }

    public void setState(DrawState state) {
        this.state = state;
    }
    
}
