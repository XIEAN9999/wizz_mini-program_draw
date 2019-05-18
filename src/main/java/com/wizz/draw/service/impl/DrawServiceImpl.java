/**
 * 
 */
package com.wizz.draw.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.wizz.draw.dao.AwardDao;
import com.wizz.draw.dao.DrawDao;
import com.wizz.draw.model.Award;
import com.wizz.draw.model.Draw;
import com.wizz.draw.model.DrawRecord;
import com.wizz.draw.service.DrawService;
import com.wizz.draw.tag.AwardState;
import com.wizz.draw.tag.DrawModel;
import com.wizz.draw.util.OneImgUpload;

/**
 * @ClassName:     DrawServiceImlp
 * @Description:TODO(这里用一句话描述这个类的作用)
 * @author:    Android_Robot
 * @date:        2019年4月28日 下午5:18:49
 *
 */
@Service
public class DrawServiceImpl implements DrawService {

    @Autowired
    private DrawDao drawDao;
    @Autowired
    private AwardDao awardDao;
    @Autowired 
    private OneImgUpload imageUtil;
    private Comparator<DrawRecord> scoreComparator;
    public DrawServiceImpl(){
        System.out.println("已创建");
        scoreComparator=new Comparator<DrawRecord>(){
            public int compare(DrawRecord o1, DrawRecord o2) {
                if(o1.getScore()<o2.getScore())
                    return 1;
                if(o1.getScore()==o2.getScore())
                    return 0;
                return -1;
            }
        };
    }
    @Override
    public int insert(Draw draw,MultipartFile comPic,MultipartFile backPic) throws Exception {
        if(comPic!=null){
            String logoId=((Map<String,String>)imageUtil.saveFile(comPic)).get("name");
            draw.setLogoId(logoId);;
        }
        if(backPic!=null){
            String picId=((Map<String,String>)imageUtil.saveFile(backPic)).get("name");
            draw.setPicID(picId);;
        }
        drawDao.insert(draw);
        return draw.getId();
    }
    @Override
    public List<DrawRecord> getWinners(int id) {
        Award award=awardDao.getAwardByDrawId(id);
        List<DrawRecord> records=drawDao.getPlayersByDrawId(id);
        for(DrawRecord r:records){
            System.out.println("参与者：id"+r.getId()+" 分数:"+r.getScore());
        }
        List<DrawRecord> winners=new ArrayList<DrawRecord>();
        int size=award.getNum()<records.size()?award.getNum():records.size();
        if(award.getModel().equals(DrawModel.high_score)){
            Collections.sort(records, scoreComparator);
            System.out.println("排序后");
            for(DrawRecord r:records){
                System.out.println("参与者：id"+r.getId()+" 分数:"+r.getScore());
            }
            winners.addAll(records.subList(0, size));
        }else if(award.getModel().equals(DrawModel.random)){
            Random r=new Random();
            int random;
            for(int i=0;i<size;i++){
                random=r.nextInt(records.size());
                winners.add(records.get(random));
                records.remove(random);
            }              
        }
        System.out.println(id+"获奖结果如下，人数："+award.getNum());
        for(DrawRecord r:winners){
            System.out.println("获奖：id"+r.getId()+" 分数:"+r.getScore());
        }
        drawDao.updateGameResults(winners);    
        drawDao.updateStateById(2, award.getDrawId());
        return winners;
    }
    @Override
    public List<Draw> getStartDrawsByPlayer(String id) {       
        return drawDao.getIniDrawsByPlayer(id);
    }
    @Override
    public List<Draw> getJoinDrawByPlayer(String id) {      
        return drawDao.getJoinDrawsByPlayer(id);
    }
  
    @Override
    public Draw getDrawById(int id) {      
        Draw draw= drawDao.getDrawById(id);
        if(draw.getState()==2){               
            System.out.println("抽奖已结束");
            draw.setAward(awardDao.getAwardByDrawIdWithPlayer(id));
        }else {
            System.out.println("抽奖未结束");
            draw.setAward(awardDao.getAwardByDrawId(id));
        }
        return draw;
    }
    @Override
    public void joinDraw(int drawId,String playerId) {
        List<Integer> awards=awardDao.getAwardsIdByDrawId(drawId);
        List<DrawRecord> recs=new ArrayList<DrawRecord>();
        for(Integer i: awards)
            recs.add(new DrawRecord(i,drawId,playerId));
       drawDao.insertRecords(recs);       
       drawDao.updatePlayerNumById(drawId);
    }
    
    @Override
    public void updateStateById(int state,int id){
        drawDao.updateStateById(state, id);
    }
 
    
    @Override
    public void updateScore(int id,String uid,int score) {
        drawDao.updateScoreById(score,id,uid);        
    }
}
