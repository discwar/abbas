package com.major.service;

import com.major.entity.UserGroupField;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户分组字段关联表 服务类
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-08-16
 */
public interface IUserGroupFieldService extends IService<UserGroupField> {

    /**
     * 添加
     * @param userGroupFieldList
     * @return
     */
    boolean addUserGroupFieldList(List<UserGroupField> userGroupFieldList);

    /**
     * 根据分组Id获取
     * @param userGroupId
     * @return
     */
    List<Map<String,String>> selectUserGroupFieldByGroupId(Long userGroupId);
}
