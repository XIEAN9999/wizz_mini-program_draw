/**
 * 
 */
package com.wizz.draw.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.wizz.draw.model.UserInfo;


/**
 * @ClassName:     WeiXinInfoDao
 * @Description:TODO
 * @author:    XIEAN99
 * @date:        2019年6月1日 下午9:49:35
 *
 */
@Repository
public interface UserInfoDao {

    @Insert("insert into user_info(open_id,session_id) "
            + "values(#{id},#{sess})" )
    public void insert(@Param("id")String openId,@Param("sess")String sessionId);
    
    @Update("update user_info set nick_name=#{name},pic_url=#{url} where session_id=#{id}")
    public void updateInfo(@Param("name")String name,@Param("url")String url,@Param("id")String id);
    
    @Update("update user_info set session_id=#{sess} where open_id=#{id}")
    public void updateSession(@Param("sess")String sess,@Param("id")String openId);
    
    @Select("select * from user_info where open_id=#{id}")
    public UserInfo getInfoByOpenId(@Param("id")String id);
    
    @Delete("delete from user_info where open_id=#{id}")
    public void delete(@Param("id")String id); 
    
}
