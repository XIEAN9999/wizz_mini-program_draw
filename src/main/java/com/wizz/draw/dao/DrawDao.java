/**
 * 
 */
package com.wizz.draw.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
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
            + "values(null,#{iniOpenId},#{theme},#{comName},#{logoId},#{picId},#{createTime})" )
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    public void insert(Draw draw);
    
    public void insertRecords(List<DrawRecord> recs);
   
    
   
    @Select("select * from draw where id=#{id} ")
    public Draw getDrawById(@Param("id")int id);
    
    //个人信息页面的两个列表
    @Results({
        @Result(property="award",column="id",
                one=@One(select="com.wizz.draw.dao.AwardDao.getAwardByDrawId")),
        @Result(property="id",column="id")
    }) 
    @Select("select * from draw where ini_open_id=#{id} ")
    public List<Draw> getIniDrawsByPlayer(@Param("id")String id);
    @Results({
        @Result(property="award",column="id",
                one=@One(select="com.wizz.draw.dao.AwardDao.getAwardByDrawId")),
        @Result(property="id",column="id")
    }) 
    @Select("select * from draw where id in (select draw_id from draw_record where player_id=#{id}) ")
    public List<Draw> getJoinDrawsByPlayer(@Param("id")String id);
    
    //抽奖信息页面--成功后获取的玩家列表
    @Select("select distinct * from draw_record where draw_id=#{id} ")
    public List<DrawRecord> getPlayersByDrawId(@Param("id")int id);
    
    //更新游戏结果
    public void updateGameResults(List<DrawRecord> res);
    
    //更新玩家数量
    @Update("update draw set player_num = player_num+1 where id=#{id}")
    public void updatePlayerNumById(@Param("id")int id);
        
    //更新抽奖状态
    @Update("update draw set state=#{state} where id=#{id}")
    public void updateStateById(@Param("state")int state,@Param("id")int id);
    
    //更新玩家分数
    @Update("update draw_record set score=#{score} where draw_id=#{id} and player_id=#{uid}")
    public void updateScoreById(@Param("score") int score,@Param("id") int id,@Param("uid") String uid);
    
   
}
