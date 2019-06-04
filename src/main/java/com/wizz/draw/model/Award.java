/**
 * 
 */
package com.wizz.draw.model;

import java.util.Date;
import java.util.List;

import com.wizz.draw.tag.AwardState;
import com.wizz.draw.tag.DrawModel;

/**
 * @ClassName:     Award
 * @Description:TODO(这里用一句话描述这个类的作用)
 * @author:    Android_Robot
 * @date:        2019年4月28日 下午3:48:13
 *
 */
public class Award {

    private int id;
    private int drawId;
    private String name;
    private int num;
    private String descrip;
    private String picId;
    private boolean initiatorJoin;
    private int model; //0:随机， 1：最高分
    private List<DrawRecord> records;

    public Award(){
        
    }
    
    public Award(int drawId, String name,String picId, int num, String descrip, boolean initiatorJoin, int model) {
        super();
        this.drawId = drawId;
        this.name = name;
        this.num = num;
        this.picId=picId;
        this.descrip = descrip;
        this.initiatorJoin = initiatorJoin;
        this.model = model;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    
    public int getDrawId() {
        return drawId;
    }
    public void setDrawId(int drawId) {
        this.drawId = drawId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getNum() {
        return num;
    }
    public void setNum(int num) {
        this.num = num;
    }
    public String getDescrip() {
        return descrip;
    }
    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }
    
    public String getPicId() {
        return picId;
    }
    public void setPicId(String picId) {
        this.picId = picId;
    }
    public boolean isInitiatorJoin() {
        return initiatorJoin;
    }
    public void setInitiatorJoin(boolean initiatorJoin) {
        this.initiatorJoin = initiatorJoin;
    }
    public int getModel() {
        return model;
    }
    public void setModel(int model) {
        this.model = model;
    }

    public List<DrawRecord> getRecords() {
        return records;
    }

    public void setRecords(List<DrawRecord> records) {
        this.records = records;
    }


}
