/**
 * 
 */
package com.wizz.draw.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.wizz.draw.model.Award;
import com.wizz.draw.tag.AwardState;



/**
 * @ClassName:     AwardDao
 * @Description:TODO(这里用一句话描述这个类的作用)
 * @author:    Android_Robot
 * @date:        2019年4月28日 下午5:30:51
 *
 */
@Repository
public interface AwardDao {

    @Insert("insert into award(id,name,draw_id,num,descrip,pic_id,initiator_join,model,create_time) "
            + "values(null,#{name},#{drawId},#{num},#{descrip},#{picId},#{initiatorJoin},#{model},#{createTime})" )
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    public void insert(Award award);
    
    @Select("select * from award where id=#{id}")
    public Award getAwardById(@Param("id") int id);
    
    
    //根据抽奖id获取,不返回玩家信息
    @Select("select * from award where draw_id=#{id}")
    public Award getAwardByDrawId(@Param("id") int id);
    //根据抽奖id获取,返回玩家信息
    @Results({
        @Result(property="records",column="id",
                many=@Many(select="com.wizz.draw.dao.DrawDao.getPlayersByAwardId")),
        @Result(property="id",column="id")
    })  
    @Select("select * from award where draw_id=#{id}")
    public Award getAwardByDrawIdWithPlayer(@Param("id") int id);
    
    
    
    @Select("select id from award where draw_id=#{id}")
    public List<Integer> getAwardsIdByDrawId(@Param("id") int id);
    
    @Select("select * from award where id in (select award_id from draw_record where player_id=#{id} "
            + "and win=1 ) ")
    public List<Award> getWinAwardsByPlayer(@Param("id")String id);
    
    @Update("update award set  begin_time=#{time} where id=#{id}")
    public void updateStartById(@Param("time") Date time,@Param("id") int id);
    
    @Update("update award set  where id=#{id}")
    public void updateFinishById(@Param("id") int id);
    
    
    
}
