package com.major.service;

import com.major.entity.UserBankCard;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.Map;

/**
 * <p>
 * 用户关联银行卡表 服务类
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-10-24
 */
public interface IUserBankCardService extends IService<UserBankCard> {

    /**
     * 查询用户的银行卡
     * @param page
     * @param userId
     * @param cardholder
     * @param bankName
     * @return
     */
    Page<Map<String, Object>> selectUserBankCardPageByUserId(Page<Map<String, Object>> page, Long userId, String cardholder, String bankName);
}
