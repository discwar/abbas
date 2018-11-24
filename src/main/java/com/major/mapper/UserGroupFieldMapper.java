package com.major.mapper;

import com.major.entity.UserGroupField;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户分组字段关联表 Mapper 接口
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-08-16
 */
public interface UserGroupFieldMapper extends BaseMapper<UserGroupField> {


    @Select("SELECT u.user_group_id,u.field_config_id,u.field_value,f.field_code  FROM user_group_field u  " +
            "LEFT JOIN field_config f ON f.id=u.field_config_id  WHERE u.user_group_id=#{userGroupId} ")
    List<Map<String,String>> selectUserGroupFieldByGroupId(@Param("userGroupId") Long userGroupId);


}
