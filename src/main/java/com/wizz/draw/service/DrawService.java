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

    public int insert(Draw draw,MultipartFile comPic,MultipartFile backPic) throws Exception ;
    public List<Draw> getStartDrawsByPlayer(String id);
    public List<Draw> getJoinDrawByPlayer(String id);
    public Draw getDrawById(int id);
    public void joinDraw(int drawId,String playerId);
    public void updateStateById(int state,int id);
    public List<DrawRecord> getWinners(int id);
    public void updateScore(int id,String uid,int score) ;
}
