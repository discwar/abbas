package com.major.service.impl;

import com.major.common.constant.Constants;
import com.major.common.enums.StatusResultEnum;
import com.major.common.exception.AgException;
import com.major.common.util.StringUtils;
import com.major.entity.LogWithdrawCash;
import com.major.mapper.LogWithdrawCashMapper;
import com.major.model.request.LogWithdrawCashRequest;
import com.major.service.ILogWithdrawCashService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 提现记录表 服务实现类
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-09-20
 */
@Service
public class LogWithdrawCashServiceImpl extends ServiceImpl<LogWithdrawCashMapper, LogWithdrawCash> implements ILogWithdrawCashService {

    @Override
    public Page<Map<String, Object>> selectLogWithdrawCashPage(Page<Map<String, Object>> page, Integer targetType, Integer cashStatus,String nickname,String bankName) {
        return  page.setRecords(baseMapper.selectLogWithdrawCashPage(page,targetType,cashStatus,nickname,bankName));
    }

    @Override
    public boolean updateLogWithdrawCash(LogWithdrawCashRequest logWithdrawCashRequest,Long id){
        if(Constants.CASH_STATUS_FAIL.equals(logWithdrawCashRequest.getCashStatus())){
            if(StringUtils.isEmpty(logWithdrawCashRequest.getCashRemarks())){
                throw new AgException(StatusResultEnum.DB_SAVE_ERROR, "请填写备注");
            }
        }
        LogWithdrawCash logWithdrawCash=new LogWithdrawCash();
        logWithdrawCash.setId(id);
        logWithdrawCash.setCashStatus(logWithdrawCashRequest.getCashStatus());
        logWithdrawCash.setCashRemarks(logWithdrawCashRequest.getCashRemarks());
        return super.updateById(logWithdrawCash);
    }

    @Override
    public Map<String,Object> selectLogWithdrawCashById(Long id){
        Map<String,Object> map=new HashMap<>();
        map.put("log_withdraw_info",baseMapper.selectLogWithdrawCashById(id));
        return map;
    }
}
