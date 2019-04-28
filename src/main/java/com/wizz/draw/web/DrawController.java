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

import com.wizz.draw.model.Draw;
import com.wizz.draw.service.AwardService;
import com.wizz.draw.service.DrawService;
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

    @RequestMapping("/insert")
    public Object insertDraw(@RequestParam (value="openid") String openId,
                             @RequestParam (value="theme") String theme,
                             @RequestParam (value="comName") String comName,
                             @RequestParam(value="logo" ,required=false) MultipartFile logo,
                             @RequestParam(value="back" ,required=false) MultipartFile backPic) throws Exception{       
        Draw draw=new Draw(openId,theme,comName);
        return ResultUtils.success(drawService.insert(draw, logo, backPic));       
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
    @RequestMapping("/get/win")
    public List<Draw> getWonDraws(@RequestParam(value="userId") String openId){
        return drawService.getWinDrawsByPlayer(openId);
    }
    @RequestMapping("/get/detail")
    public Draw getDrawDetail(@RequestParam(value="id") int id){
        return drawService.getDrawById(id);
    }
    @RequestMapping("/get/result")
    public Object updateWinner(@RequestParam(value="id") int id,
                               @RequestParam(value="aid") int aid){
        drawService.getWinners(aid, id);
        return ResultUtils.success();
    }
}
