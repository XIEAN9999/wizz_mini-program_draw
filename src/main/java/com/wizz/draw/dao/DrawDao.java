/**
 * 
 */
package com.wizz.draw.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
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

    @Insert("insert into draw(id,ini_open_id,theme,com_name,logo_id,pic_id,create_time,player_num) "
            + "values(null,#{iniOpenId},#{theme},#{comName},#{logoId},#{picId},now(),#{playerNum})" )
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    public void insert(Draw draw);
    
    @Insert("insert into draw_record(id,draw_id,player_id,player_name,player_pic_url,update_time)"
            + " values(null,#{drawId},#{playerId},#{playerName},#{playerPicUrl},#{updateTime})")
    public void insertRecord(DrawRecord rec);
   
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
    
    //获取随机模式获奖用户列表
    @Select("select * from draw_record where draw_id=#{id} and win=1  order by score,update_time ")
    public List<DrawRecord>getRModelWinnerList(@Param("id")int id);
    
    //获取最高分模式用户列表
    @Select("select * from draw_record where draw_id=#{id} and win=1 and score!=0 order by score,update_time ")
    public List<DrawRecord>getHModelWinnerList(@Param("id")int id);
    
    //获取随机模式未获奖用户列表
    @Select("select * from draw_record where draw_id=#{id} and win=0  order by score,update_time ")
    public List<DrawRecord>getRModelLosersList(@Param("id")int id);
    
    //获取最高分模式未获奖用户列表
    @Select("select * from draw_record where draw_id=#{id} and win=0 and score!=0 order by score,update_time ")
    public List<DrawRecord>getHModelLosersList(@Param("id")int id);
    
    //根据用户id和抽奖id获取记录
    @Select("select id from draw_record where player_id=#{uid} and draw_id=#{did}")
    public Integer getRecord(@Param("uid")String uid,@Param("did")int did);
    
    //根据用户id和抽奖id获取是否上传分数
    @Select("select ifupdate from draw_record where player_id=#{uid} and draw_id=#{did}")
    public Integer getUpdateState(@Param("uid")String uid,@Param("did")int did) ;
    
    //抽奖信息页面--成功后获取的玩家列表
    @Select("select distinct * from draw_record where draw_id=#{id} ")
    public List<DrawRecord> getPlayersByDrawId(@Param("id")int id);
    
    //更新游戏结果
    public void updateGameResults(List<DrawRecord> res);
    
    //更新玩家数量
    @Update("update draw set player_num = player_num+1 where id=#{id}")
    public void updatePlayerNumById(@Param("id")int id);
        
    //更新抽奖状态
    @Update("update draw set state=1,begin_time=#{time} where id=#{id}")
    public void updateToStart(@Param("id")int id,@Param("time")long time);
    
    @Update("update draw set state=2,finish_time=now() where id=#{id}")
    public void updateToFinish(@Param("time")Date time,@Param("id")int id);
    
    //更新抽奖状态
    @Update("update draw set state=3 where id=#{id}")
    public void updateToCancle(@Param("id")int id);
    
    //更新玩家分数
    @Update("update draw_record set score=#{score},ifupdate=1 where draw_id=#{id} and player_id=#{uid}")
    public void updateScoreById(@Param("score") int score,@Param("id") int id,@Param("uid") String uid);
    
    //删除抽奖
    @Delete("delete from draw where id=#{id}")
    public void deleteDrawById(@Param("id")int id);
    
    //删除发起的抽奖
    @Delete("delete from draw where ini_open_id=#{id}")
    public void deleteIniDraw(@Param("id")String id);
    
    //删除抽奖记录
    @Delete("delete from draw_record where player_id=#{id} and draw_id=#{did}")
    public void deleteRecord(@Param("id")String id,@Param("did")int drawId);
 
    //删除获奖记录
    @Delete("delete from draw_record where player_id=#{id}  and draw_id=#{did} and win=1")
    public void deleteWonRecord(@Param("id")String id,@Param("did")int drawId);
   
}
