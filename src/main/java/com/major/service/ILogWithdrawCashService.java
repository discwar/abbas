package com.major.service;

import com.major.entity.LogWithdrawCash;
import com.major.model.request.LogWithdrawCashRequest;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.Map;

/**
 * <p>
 * 提现记录表 服务类
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-09-20
 */
public interface ILogWithdrawCashService extends IService<LogWithdrawCash> {

    /**
     * 获取提现记录表分页列表
     * @param page
     * @param targetType
     * @param cashStatus
     * @return
     */
    Page<Map<String, Object>> selectLogWithdrawCashPage(Page<Map<String, Object>> page, Integer targetType, Integer cashStatus,String nickname,String bankName);

    /**
     * 处理审核
     * @param logWithdrawCashRequest
     * @param id
     * @return
     */
    boolean updateLogWithdrawCash(LogWithdrawCashRequest logWithdrawCashRequest, Long id);

    /**
     * 获取当前信息
     * @param id
     * @return
     */
    Map<String,Object> selectLogWithdrawCashById(Long id);
}
