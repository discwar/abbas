package com.major.mapper;

import com.major.entity.FieldConfig;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 用户分组字段表 Mapper 接口
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-08-16
 */
public interface FieldConfigMapper extends BaseMapper<FieldConfig> {

    @Select("SELECT * FROM  field_config WHERE  table_name='user_group'")
    List<FieldConfig> selectFieldConfigByTable();
}
