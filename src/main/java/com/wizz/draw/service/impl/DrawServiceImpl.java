/**
 * 
 */
package com.wizz.draw.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.wizz.draw.dao.AwardDao;
import com.wizz.draw.dao.DrawDao;
import com.wizz.draw.model.Award;
import com.wizz.draw.model.Draw;
import com.wizz.draw.model.DrawRecord;
import com.wizz.draw.service.DrawService;
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
    @Value("${web.upload-path}")
    private String filePath;
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
    public int insert(Draw draw){      
        drawDao.insert(draw);
        return draw.getId();
    }
    @Override
    public void insertRecord(int drawId,String playerId,String name,String url){
        DrawRecord rec=new DrawRecord(drawId,playerId,name,url);
        drawDao.insertRecord(rec);       
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
        if(award.getModel()==1){
            Collections.sort(records, scoreComparator);
            System.out.println("排序后");
            for(DrawRecord r:records){
                System.out.println("参与者：id"+r.getId()+" 分数:"+r.getScore());
            }
            winners.addAll(records.subList(0, size));
        }else if(award.getModel()==0){
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
        drawDao.updateToFinish(new Date(),award.getDrawId());
        return winners;
    }
    
    @Override
    public List<Draw> getStartDrawsByPlayer(String id) {       
        return drawDao.getIniDrawsByPlayer(id);
    }
    
    @Override
    public List<DrawRecord> getWinnersList(int drawId){
        int model=awardDao.getAwardByDrawId(drawId).getModel();
        return model==0?drawDao.getRModelWinnerList(drawId):drawDao.getHModelWinnerList(drawId);
    }
    
    @Override
    public List<DrawRecord> getLosersList(int drawId){
        int model=awardDao.getAwardByDrawId(drawId).getModel();
        return model==0?drawDao.getRModelLosersList(drawId):drawDao.getHModelLosersList(drawId);
    }
    
    @Override
    public List<Draw> getJoinDrawByPlayer(String id) {      
        return drawDao.getJoinDrawsByPlayer(id);
    }
  
    @Override
    public Draw getDrawById(int id) {      
        Draw draw= drawDao.getDrawById(id);
        draw.setAward(awardDao.getAwardByDrawIdWithPlayer(id));
        return draw;
    }
    
    @Override
    public void joinDraw(int drawId,String playerId,String name,String url) {
        DrawRecord rec=new DrawRecord(drawId,playerId,name,url);
        drawDao.insertRecord(rec);       
        drawDao.updatePlayerNumById(drawId);
    }
    
    @Override
    public void updateState(int id,int state,long time){
        if(state==1){
            System.out.println(id+"更新到开始状态 ,前端时间戳:"+time);
            drawDao.updateToStart(id,time);
        }        
        else if(state==2){
            getWinners(id);
        }
        else if(state==3){
            drawDao.updateToCancle(id);
        }
    } 
    
    @Override
    public void updateScore(int id,String uid,int score) {
        drawDao.updateScoreById(score,id,uid);        
    }
    
    @Override
    public void deleteDraw(int id) {
        Draw draw=drawDao.getDrawById(id);
        File pic=new File(filePath+draw.getPicId());
        if(pic.exists()&&pic.isFile()) //删除抽奖图片
            pic.delete();
        pic=new File(filePath+draw.getLogoId());//删除公司logo
        if(pic.exists()&&pic.isFile())
            pic.delete();
        Award award=awardDao.getAwardByDrawId(draw.getId());
        if(award!=null){
            pic=new File(filePath+award.getPicId());//删除奖品图片
            if(pic.exists()&&pic.isFile())
                pic.delete();
        }        
        drawDao.deleteDrawById(id);  // award 将被级联删除
    }

    @Override
    public void deleteRecord(String userId,int drawId) {
        drawDao.deleteRecord(userId,drawId);        
    }

    @Override
    public void deleteWonRecord(String userId,int drawId) { 
        drawDao.deleteWonRecord(userId,drawId);
    }

    @Override
    public void deleteIniRecord(String userId) {
        drawDao.deleteIniDraw(userId);
        
    }
    
    @Override
    public int ifJoinDraw(String userId,int drawId){
        return drawDao.getRecord(userId, drawId)!=null?1:0;
    }
    @Override
    public Integer ifUpdateRecord(String userId,int drawId){
        return drawDao.getUpdateState(userId, drawId);
    }
}
