package com.major.service;

import com.major.entity.FieldConfig;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 用户分组字段表 服务类
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-08-16
 */
public interface IFieldConfigService extends IService<FieldConfig> {

    List<FieldConfig> selectFieldConfigByTable();
}
