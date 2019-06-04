/**
 * 
 */
package com.wizz.draw.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.wizz.draw.model.Award;
import com.wizz.draw.model.Draw;
import com.wizz.draw.model.DrawRecord;

/**
 * @ClassName:     DrawService
 * @Description:TODO(这里用一句话描述这个类的作用)
 * @author:    Android_Robot
 * @date:        2019年4月28日 下午5:02:17
 *
 */
public interface DrawService {

    public int insert(Draw draw) ;
    public List<Draw> getStartDrawsByPlayer(String id);
    public List<Draw> getJoinDrawByPlayer(String id);
    public Draw getDrawById(int id);
    public List<DrawRecord> getWinners(int id);
    public List<DrawRecord> getWinnersList(int drawId);
    public List<DrawRecord> getLosersList(int drawId);
    public void joinDraw(int drawId,String playerId,String name,String url);
    public void updateState(int id,int state,long time);    
    public void updateScore(int id,String uid,int score) ;
    public void deleteDraw(int id);
    public void deleteRecord(String userId,int drawId);
    public void deleteWonRecord(String userId,int drawId);
    public void deleteIniRecord(String userId);
    public void insertRecord(int drawId, String playerId, String name, String url);
    public int ifJoinDraw(String userId,int drawId);
    public Integer ifUpdateRecord(String userId, int drawId);
    
}
