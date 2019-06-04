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
import com.wizz.draw.tag.AwardState;

/**
 * @ClassName:     AwardService
 * @Description:TODO(这里用一句话描述这个类的作用)
 * @author:    Android_Robot
 * @date:        2019年4月28日 下午7:50:39
 *
 */
public interface AwardService {

    public int insert(Award award) ;
    public Award getAwardByDraw(int id);
    public Award getAwardById(int id);
    public List<Award> getWinAwardByPlayer(String id);
}
