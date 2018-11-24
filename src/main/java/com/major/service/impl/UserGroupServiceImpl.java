package com.major.service.impl;

import com.major.common.constant.Constants;
import com.major.entity.UserGroup;
import com.major.entity.UserGroupField;
import com.major.mapper.UserGroupMapper;
import com.major.model.request.UserGroupRequest;
import com.major.service.IUserGroupFieldService;
import com.major.service.IUserGroupService;
import com.major.service.IUserService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户组表 服务实现类
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-08-15
 */
@Service
public class UserGroupServiceImpl extends ServiceImpl<UserGroupMapper, UserGroup> implements IUserGroupService {

    @Autowired
    private IUserService userService;

    @Autowired
    private IUserGroupFieldService userGroupFieldService;
    @Override
   public boolean addUserGroup(UserGroupRequest userGroupRequest)  {
        //searchType筛选状态:0-xx刚注册的用户;1-xx消费超过x元的用户;2-xx消费未超过x元的用户;3-24内未活跃的用户
        UserGroup userGroup=new UserGroup();
        BeanUtils.copyProperties(userGroupRequest, userGroup);
        userGroup.setStatus(Constants.STATUS_NORMAL);
        super.insert(userGroup);

        List<UserGroupField> userGroupFieldList=new ArrayList<>();
        for (Map<Integer, String> m : userGroupRequest.getGroupKeyValue()){
            for (Integer k : m.keySet()){
                UserGroupField userGroupField=new UserGroupField();
                userGroupField.setUserGroupId(userGroup.getId().intValue());
                userGroupField.setFieldConfigId(k);
                userGroupField.setFieldValue(m.get(k));
                userGroupFieldList.add(userGroupField);
            }
        }
       return userGroupFieldService.addUserGroupFieldList(userGroupFieldList);
   }

    @Override
    public Page<Map<String, Object>> selectUserGroupPage(Page<Map<String, Object>> page) {
        return page.setRecords(baseMapper.selectUserGroupPage(page));
    }

    @Override
    public   List<Map<String, Object>> selectUserGroupList(){
        return baseMapper.selectUserGroupList();
    }
    @Override
    public boolean updateUserGroup(UserGroupRequest userGroupRequest,Long groupId) {
        UserGroup userGroup=new UserGroup();
        userGroup.setGroupName(userGroupRequest.getGroupName());
        userGroup.setId(groupId);
        return super.updateById(userGroup);
    }
    @Override
    public boolean deleteUserGroup(Long groupId) {
        UserGroup userGroup=new UserGroup();
        userGroup.setId(groupId);
        return userGroup.deleteById();
    }

    @Override
    public  UserGroup selectUserGroupById(Long groupId) {
        return super.selectById(groupId);
    }
}
