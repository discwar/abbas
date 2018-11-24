package com.major.service.impl;

import com.major.entity.GroupBuying;
import com.major.mapper.GroupBuyingMapper;
import com.major.service.IGroupBuyingService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 团购方案表 服务实现类
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-08-31
 */
@Service
public class GroupBuyingServiceImpl extends ServiceImpl<GroupBuyingMapper, GroupBuying> implements IGroupBuyingService {

    @Override
    public boolean addGroupBuyings(List<GroupBuying> groupBuyingList){
     return super.insertBatch(groupBuyingList);
    }
}
