/**
 * 
 */
package com.wizz.draw.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.wizz.draw.dao.AwardDao;
import com.wizz.draw.model.Award;
import com.wizz.draw.service.AwardService;
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
    private OneImgUpload imageUtil;

    @Override
    public int insert(Award award,MultipartFile pic) throws Exception {
        if(pic!=null){
            String picId=((Map<String,String>)imageUtil.saveFile(pic)).get("name");
            award.setPicId(picId);;
        }
        awardDao.insert(award);       
        return award.getId();
    }

    @Override
    public List<Award> getListByDraw(int id) {       
        return awardDao.getAwardsByDrawId(id);
    }
    
}
