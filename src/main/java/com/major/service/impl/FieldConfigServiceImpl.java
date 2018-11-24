package com.major.service.impl;

import com.major.entity.FieldConfig;
import com.major.mapper.FieldConfigMapper;
import com.major.service.IFieldConfigService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户分组字段表 服务实现类
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-08-16
 */
@Service
public class FieldConfigServiceImpl extends ServiceImpl<FieldConfigMapper, FieldConfig> implements IFieldConfigService {

    @Override
    public List<FieldConfig> selectFieldConfigByTable() {
        return baseMapper.selectFieldConfigByTable();
    }
}
