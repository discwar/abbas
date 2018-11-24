package com.major.service;

import com.major.entity.MoneyLog;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.Map;

/**
 * <p>
 * 商家资金流水表 服务类
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-11-22
 */
public interface IMoneyLogService extends IService<MoneyLog> {

    /**
     * 查询资金流水
     * @param page
     * @param sysUserId
     * @return
     */
    Page<Map<String, Object>> selectMoneyLogPageBySysUserId(Page<Map<String, Object>> page, Long sysUserId);
}
