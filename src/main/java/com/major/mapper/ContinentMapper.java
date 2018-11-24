package com.major.mapper;

import com.major.entity.Continent;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 大洲表 Mapper 接口
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-10-24
 */
public interface ContinentMapper extends BaseMapper<Continent> {

    @Select("select id ,name ,status from continent where status=1 order by id ")
    List<Map<String,Object>> selectContinent();

}
