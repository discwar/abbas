package com.major.service;

import com.major.entity.Banks;
import com.major.model.request.BanksRequest;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.Map;

/**
 * <p>
 * 银行表 服务类
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-09-20
 */
public interface IBanksService extends IService<Banks> {

    /**
     * 获取银行数据分页列表
     * @param page
     * @param bankName
     * @return
     */
    Page<Map<String, Object>> selectBanksPage(Page<Map<String, Object>> page, String bankName);

    /**
     * 添加银行数据
     * @param banksRequest
     * @return
     */
    boolean addBanks(BanksRequest banksRequest);

    /**
     * 修改银行数据
     * @param banksRequest
     * @param id
     * @return
     */
    boolean updateBanks(BanksRequest banksRequest,Long id);

    /**
     * 删除
     * @param id
     * @return
     */
    boolean deleteBanks(Long id);

    Map<String, Object> selectBanksList();
}
