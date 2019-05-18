/**
 * 
 */
package com.wizz.draw.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.wizz.draw.dao.AwardDao;
import com.wizz.draw.dao.DrawDao;
import com.wizz.draw.model.Award;
import com.wizz.draw.model.DrawRecord;
import com.wizz.draw.service.AwardService;
import com.wizz.draw.tag.AwardState;
import com.wizz.draw.tag.DrawModel;

import com.wizz.draw.util.OneImgUpload;

/**
 * @ClassName:     AwardServiceImpl
 * @Description:TODO(这里用一句话描述这个类的作用)
 * @author:    Android_Robot
 * @date:        2019年4月28日 下午7:52:49
 *
 */
@Service
public class AwardServiceImpl implements AwardService{

    @Autowired
    private AwardDao awardDao;
    @Autowired
    private DrawDao drawDao;
    @Autowired 
    private OneImgUpload imageUtil;
   
    @Override
    public int insert(Award award,MultipartFile pic) throws Exception {
        if(pic!=null){
            String picId=((Map<String,String>)imageUtil.saveFile(pic)).get("name");
            award.setPicId(picId);;
        }
        awardDao.insert(award);    
        System.out.println("新增奖品："+award.getName()+"*"+award.getNum()+";");
        return award.getId();
    }
    
    @Override
    public Award getAwardByDraw(int id) {       
        Award award=awardDao.getAwardByDrawId(id);
        return award;
    }

    @Override
    public Award getAwardById(int id){
        return awardDao.getAwardById(id);
    }
    
    @Override
    public List<Award> getWinAwardByPlayer(String id) {
        return awardDao.getWinAwardsByPlayer(id);
    }

}
