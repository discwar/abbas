package com.major.service.impl;

import com.major.entity.GroupPicking;
import com.major.mapper.GroupPickingMapper;
import com.major.service.IGroupPickingService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 组团采摘表 服务实现类
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-08-31
 */
@Service
public class GroupPickingServiceImpl extends ServiceImpl<GroupPickingMapper, GroupPicking> implements IGroupPickingService {


    @Override
    public Page<Map<String, Object>>  selectGroupPickingPage(Page<Map<String, Object>>  page, String goodsName,String shopName ) {
        return page.setRecords(baseMapper.selectGroupPickingPage(page,goodsName,shopName));
    }

}
