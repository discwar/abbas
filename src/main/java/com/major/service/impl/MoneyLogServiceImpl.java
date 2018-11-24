package com.major.service.impl;

import com.major.entity.MoneyLog;
import com.major.mapper.MoneyLogMapper;
import com.major.service.IMoneyLogService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 商家资金流水表 服务实现类
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-11-22
 */
@Service
public class MoneyLogServiceImpl extends ServiceImpl<MoneyLogMapper, MoneyLog> implements IMoneyLogService {


    @Override
    public Page<Map<String, Object>> selectMoneyLogPageBySysUserId(Page<Map<String, Object>> page, Long sysUserId) {
        Wrapper ew = new EntityWrapper();
        ew.where("sys_user_id={0}",sysUserId);
        ew.orderBy(" create_time DESC ");
        return page.setRecords(baseMapper.selectMoneyLogPageBySysUserId(page,ew));
    }
}
