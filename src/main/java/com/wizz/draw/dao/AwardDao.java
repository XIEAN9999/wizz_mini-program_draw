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

import com.wizz.draw.model.Award;



/**
 * @ClassName:     AwardDao
 * @Description:TODO(这里用一句话描述这个类的作用)
 * @author:    Android_Robot
 * @date:        2019年4月28日 下午5:30:51
 *
 */
@Repository
public interface AwardDao {

    @Insert("insert into draw(id,name,num,descrip,pic_id,initiator_join,model) "
            + "values(null,#{name},#{num},#{descrip},#{picId},#{initiatorJoin},#{model})" )
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    public void insert(Award award);
    
    @Select("select * from award where draw_id=#{id}")
    public List<Award> getAwardsByDrawId(@Param("id") int id);
    

    @Select("select * from award where id=#{id}")
    public Award getAwardById(@Param("id") int id);
    
    
    
    
    
    
    
    
}
