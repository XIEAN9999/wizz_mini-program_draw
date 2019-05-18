/**
 * 
 */
package com.wizz.draw.web;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.wizz.draw.model.Award;
import com.wizz.draw.model.Draw;
import com.wizz.draw.service.AwardService;
import com.wizz.draw.service.DrawService;
import com.wizz.draw.tag.DrawModel;
import com.wizz.draw.util.ResultData;
import com.wizz.draw.util.ResultUtils;

/**
 * @ClassName:     DrawController
 * @Description:TODO
 * @author:    XIEAN99
 * @date:        2019年4月28日 下午9:07:57
 *
 */
@RestController
@RequestMapping("/draw")
public class DrawController {

    @Autowired
    private DrawService drawService;
    @Autowired
    private AwardService awardService;
    
    @RequestMapping("/insert")
    public Object insertDraw(@RequestParam (value="openid") String openId,
                             @RequestParam (value="theme") String theme,
                             @RequestParam (value="comName") String comName,
                             @RequestParam(value="logo" ,required=false) MultipartFile logo,
                             @RequestParam(value="back" ,required=false) MultipartFile backPic,
                             @RequestParam(value="a_name") String name,
                             @RequestParam(value="a_num") int num,
                             @RequestParam(value="a_desc") String desc,
                             @RequestParam(value="a_join") boolean join,
                             @RequestParam(value="a_model") int model,
                             @RequestParam(value="a_pic" ,required=false) MultipartFile pic) throws Exception{       
        Draw draw=new Draw(openId,theme,comName); 
        drawService.insert(draw, logo, backPic);
        Award award=new Award(draw.getId(),name,num,desc,join,DrawModel.parse(model));
        awardService.insert(award, pic);
        return ResultUtils.success(draw.getId());       
    }
    
    @RequestMapping("/join")
    public Object join(@RequestParam(value="userId") String openId,
                       @RequestParam(value="drawId") int drawId){
        drawService.joinDraw(drawId,openId);
        return ResultUtils.success();
    }
    @RequestMapping("/get/start")
    public List<Draw> getStartDraws(@RequestParam(value="userId") String openId){
        return drawService.getStartDrawsByPlayer(openId);
    }
    @RequestMapping("/get/joined")
    public List<Draw> getJoinedDraws(@RequestParam(value="userId") String openId){
        return drawService.getJoinDrawByPlayer(openId);
    }   
    @RequestMapping("/get/detail")
    public Draw getDrawDetail(@RequestParam(value="id") int id){
        return drawService.getDrawById(id);
    }
    @RequestMapping("/get/win")
    public List<Award> getWonDraws(@RequestParam(value="userId") String openId){
        return awardService.getWinAwardByPlayer(openId);
    }
    @RequestMapping("/get/result")
    public Object updateWinner(@RequestParam(value="id") int id){
        drawService.getWinners(id);
        return ResultUtils.success();
    }
    @RequestMapping("/get/award")
    public Award getAwardByDraw(@RequestParam(value="id") int drawId){
        return awardService.getAwardByDraw(drawId);
    }
    @RequestMapping("/update/start")
    public Object updateState( @RequestParam(value="id") int id){
        drawService.updateStateById(1, id);
        return ResultUtils.success();
    }
    @RequestMapping("/update/score")
    public Object updateScore(@RequestParam(value="id") int id,
                              @RequestParam(value="userId") String userId,
                              @RequestParam(value="score") int score){
        drawService.updateScore(id,userId,score);
        return ResultUtils.success();
    }
}
