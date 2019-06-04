/**
 * 
 */
package com.wizz.draw.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.wizz.draw.model.Award;
import com.wizz.draw.model.Draw;
import com.wizz.draw.service.AwardService;
import com.wizz.draw.service.DrawService;
import com.wizz.draw.util.OneImgUpload;
import com.wizz.draw.util.ResultUtils;
import com.wizz.draw.util.UserRedisUtil;

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
    @Autowired 
    private OneImgUpload imageUtil;
    @Autowired
    private UserRedisUtil userUtil;
    
    @RequestMapping("/insert")
    public Object insertDraw(@RequestParam (value="openid") String sessionId,
                             @RequestParam (value="theme") String theme,
                             @RequestParam (value="comName") String comName,
                             @RequestParam(value="logo" ,required=false) String logo,
                             @RequestParam(value="back" ,required=false) String backPic,
                             @RequestParam(value="a_name") String name,
                             @RequestParam(value="a_num") int num,
                             @RequestParam(value="a_desc") String desc,
                             @RequestParam(value="a_join") boolean join,
                             @RequestParam(value="a_model") int model,
                             @RequestParam(value="a_pic" ,required=false) String pic,HttpSession sess) throws Exception{       
        String openId=userUtil.getOpenId(sessionId);
        System.out.println("openid是"+openId);
        Map<String,String> info=userUtil.getInfo(sessionId);
        Draw draw=new Draw(openId,theme,comName,logo,backPic,(join?1:0)); 
        drawService.insert(draw);
        Award award=new Award(draw.getId(),name,pic,num,desc,join,model);
        awardService.insert(award);
        if(join)
            drawService.insertRecord(draw.getId(),openId,info.get("nickName"),info.get("picUrl"));
        return ResultUtils.success(draw.getId());       
    }
    
    @RequestMapping("/upload/file")
    public String uploadFile(@RequestParam("file") MultipartFile[] files) throws Exception{
        @SuppressWarnings("unchecked")
        Map<String,String> res=(Map<String, String>) imageUtil.saveFile(files[0]);
        return res.get("name");
    }
    @RequestMapping("/join")
    public Object join(@RequestParam(value="userId") String sessionId,
                       @RequestParam(value="drawId") int drawId,HttpSession sess) throws Exception{
        String openId=userUtil.getOpenId(sessionId);
        Map<String,String> info=userUtil.getInfo(sessionId);
        drawService.joinDraw(drawId,openId,info.get("nickName"),info.get("picUrl"));
        return ResultUtils.success();
    }
    @RequestMapping("/get/start")
    public List<Draw> getStartDraws(@RequestParam(value="userId") String sessionId,HttpSession sess) throws Exception{
        String openId=userUtil.getOpenId(sessionId);
        return drawService.getStartDrawsByPlayer(openId);
    }
    @RequestMapping("/get/joined")
    public List<Draw> getJoinedDraws(@RequestParam(value="userId") String sessionId,HttpSession sess) throws Exception{
        String openId=userUtil.getOpenId(sessionId);
        return drawService.getJoinDrawByPlayer(openId);
    }   
    @RequestMapping("/get/detail")
    public Draw getDrawDetail(@RequestParam(value="id") int id){
        return drawService.getDrawById(id);
    }
    @RequestMapping("/get/win")
    public List<Award> getWonDraws(@RequestParam(value="userId") String sessionId,HttpSession sess) throws Exception{
        String openId=userUtil.getOpenId(sessionId);
        return awardService.getWinAwardByPlayer(openId);
    }
    @RequestMapping("/get/winnerlist")
    public Object getRankingList(@RequestParam(value="id") int drawId){
        Map<String,Object> res=new HashMap<String,Object>(); 
        res.put("winners", drawService.getWinnersList(drawId));
        res.put("others",drawService.getLosersList(drawId));
        return res;
    }
    @RequestMapping("/get/award")
    public Award getAwardByDraw(@RequestParam(value="id") int drawId){
        return awardService.getAwardByDraw(drawId);
    }
    @RequestMapping("/update/state")
    public Object updateState(@RequestParam(value="id") int id,
                              @RequestParam(value="state") int state,
                              @RequestParam(value="time",required=false)Long time){
        time=time==null?0:time;
        drawService.updateState(id,state,time);
        return ResultUtils.success();
    }
    @RequestMapping("/update/score")
    public Object updateScore(@RequestParam(value="id") int id,
                              @RequestParam(value="userId") String sessionId,
                              @RequestParam(value="score") int score,HttpSession sess) throws Exception{
        String openId=userUtil.getOpenId(sessionId);
        drawService.updateScore(id,openId,score);
        return ResultUtils.success();                
    }
    @RequestMapping("/delete")
    public Object deleteDraw(@RequestParam(value="id")int id){
        drawService.deleteDraw(id);
        return ResultUtils.success();
    }
    @RequestMapping("/delete/joined")
    public Object deleteJoinedDraw(@RequestParam(value="userId") String sessionId,
                                   @RequestParam(value="drawId") int drawId) throws Exception{
        String openId=userUtil.getOpenId(sessionId);
        drawService.deleteRecord(openId,drawId);
        return drawService.getJoinDrawByPlayer(openId);
    }
    @RequestMapping("/delete/win")
    public Object deleteWonDraw(@RequestParam(value="userId") String sessionId,
                                @RequestParam(value="drawId") int drawId) throws Exception{
        String openId=userUtil.getOpenId(sessionId);
        System.out.println("调用删除获奖接口"+openId+" "+drawId);
        drawService.deleteWonRecord(openId,drawId);
        return awardService.getWinAwardByPlayer(openId);
    }
    @RequestMapping("/delete/start")
    public Object deleteIniDraw(@RequestParam(value="userId") String sessionId,
                                @RequestParam(value="drawId") int drawId) throws Exception{
        String openId=userUtil.getOpenId(sessionId);
        drawService.deleteDraw(drawId);
        return drawService.getStartDrawsByPlayer(openId);
    }
    @RequestMapping("/ifjoin")
    public Object ifJoin(@RequestParam(value="userId") String sessionId,
                         @RequestParam(value="drawId") int drawId) throws Exception {
        String openId=userUtil.getOpenId(sessionId);
        return ResultUtils.success(drawService.ifJoinDraw(openId, drawId));
    }
    
    @RequestMapping("/ifupdate")
    public Object ifUpdate(@RequestParam(value="userId") String sessionId,
                         @RequestParam(value="drawId") int drawId) throws Exception{
        String openId=userUtil.getOpenId(sessionId);
        return ResultUtils.success(drawService.ifUpdateRecord(openId, drawId));
    }
}
