/**
 * 
 */
package com.wizz.draw.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.wizz.draw.model.Award;
import com.wizz.draw.service.AwardService;
import com.wizz.draw.tag.DrawModel;
import com.wizz.draw.util.ResultUtils;

/**
 * @ClassName:     AwardController
 * @Description:TODO
 * @author:    XIEAN99
 * @date:        2019年4月28日 下午9:52:01
 *
 */
@RestController
@RequestMapping("/award")
public class AwardController {

    @Autowired
    private AwardService awardService;

    @RequestMapping("/insert")
    public Object insertAward(@RequestParam(value="drawId") int drawId,
                              @RequestParam(value="name") String name,
                              @RequestParam(value="num") int num,
                              @RequestParam(value="desc") String desc,
                              @RequestParam(value="join") boolean join,
                              @RequestParam(value="model") int model,
                              @RequestParam(value="pic" ,required=false) MultipartFile pic) throws Exception{
        Award award=new Award(drawId,name,num,desc,join,DrawModel.parse(model));
        awardService.insert(award, pic);
        return ResultUtils.success();
    }
    @RequestMapping("/get/detail")
    public List<Award> getAwardByDraw(@RequestParam(value="id") int drawId){
        return awardService.getListByDraw(drawId);
    }
    
}
