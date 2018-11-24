package com.major.service.impl;

import com.major.entity.UserGroupField;
import com.major.mapper.UserGroupFieldMapper;
import com.major.service.IUserGroupFieldService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户分组字段关联表 服务实现类
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-08-16
 */
@Service
public class UserGroupFieldServiceImpl extends ServiceImpl<UserGroupFieldMapper, UserGroupField> implements IUserGroupFieldService {

   @Override
   public   boolean addUserGroupFieldList(List<UserGroupField> userGroupFieldList) {
        return super.insertBatch(userGroupFieldList);
    }

    @Override
    public List<Map<String,String>> selectUserGroupFieldByGroupId( Long userGroupId) {
       return baseMapper.selectUserGroupFieldByGroupId(userGroupId);
    }
}
