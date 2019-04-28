/**
 * 
 */
package com.wizz.draw.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.wizz.draw.model.Draw;
import com.wizz.draw.model.DrawRecord;


/**
 * @ClassName:     DrawDao
 * @Description:TODO(这里用一句话描述这个类的作用)
 * @author:    Android_Robot
 * @date:        2019年4月28日 下午4:39:14
 *
 */
@Repository
public interface DrawDao {

    @Insert("insert into draw(id,ini_open_id,theme,com_name,logo_id,pic_id,create_time) "
            + "values(null,#{initiatorOpenId},#{theme},#{comName},#{logoId},#{picId},#{createTime})" )
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    public void insert(Draw draw);
   
    @Insert("insert into draw_record(id,draw_Id,player_id) "
            + "values(null,#{drawId},#{playerId})" )
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    public void insertRecord(DrawRecord rec);
    //抽奖信息页面--基础信息
    @Select("select * from draw where id=#{id} ")
    public Draw getDrawById(@Param("id")int id);
    
    //个人信息页面的三个列表
    @Select("select * from draw where ini_open_id=#{id} ")
    public List<Draw> getIniDrawsByPlayer(@Param("id")String id);
    
    @Select("select * from draw where id in (select draw_id from draw_record where player_id=#{id}) ")
    public List<Draw> getJoinDrawsByPlayer(@Param("id")String id);
    
    @Select("select * from draw where id in (select draw_id from draw_record where player_id=#{id} "
            + "and result=1 ) ")
    public List<Draw> getWinDrawsByPlayer(@Param("id")String id);
    //抽奖信息页面--成功后获取的玩家列表
    @Select("select * from draw_record where draw_id=#{id} ")
    public List<DrawRecord> getPlayersByDrawId(@Param("id")int id);
    //更新游戏结果
    public void updateGameResults(List<DrawRecord> res);
    
    
    
    
    
    
    
    
}
