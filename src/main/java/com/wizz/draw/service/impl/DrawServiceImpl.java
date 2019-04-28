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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.wizz.draw.dao.AwardDao;
import com.wizz.draw.dao.DrawDao;
import com.wizz.draw.model.Award;
import com.wizz.draw.model.Draw;
import com.wizz.draw.model.DrawRecord;
import com.wizz.draw.service.DrawService;
import com.wizz.draw.tag.DrawModel;
import com.wizz.draw.tag.DrawState;
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
        scoreComparator=new Comparator<DrawRecord>(){
            public int compare(DrawRecord o1, DrawRecord o2) {
                if(o1.getScore()<o2.getScore())
                    return -1;
                if(o1.getScore()==o2.getScore())
                    return 0;
                return 1;
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
    public List<DrawRecord> getWinners(int aid,int did) {
        Award award=awardDao.getAwardById(aid);
        List<DrawRecord> records=drawDao.getPlayersByDrawId(did);
        List<DrawRecord> winners=new ArrayList<DrawRecord>();
        if(award.getModel().equals(DrawModel.high_score)){
            Collections.sort(records, scoreComparator);
            winners.addAll(records.subList(0, award.getNum()));
        }else if(award.getModel().equals(DrawModel.random)){
            Random r=new Random();
            int random;
            for(int i=0;i<award.getNum();i++){
                random=r.nextInt(records.size());
                winners.add(records.get(random));
            }              
        }
        drawDao.updateGameResults(winners);    
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
    public List<Draw> getWinDrawsByPlayer(String id) {
        return drawDao.getWinDrawsByPlayer(id);
    }
    @Override
    public Draw getDrawById(int id) {      
        Draw draw= drawDao.getDrawById(id);
        if(draw.getState().equals(DrawState.ongoing))
            return draw;
        draw.setRecords(drawDao.getPlayersByDrawId(id));
            return draw;
    }
    @Override
    public void joinDraw(int drawId,String playerId) {
        DrawRecord rec=new DrawRecord(drawId,playerId);
       drawDao.insertRecord(rec);
        
    }

 
}
