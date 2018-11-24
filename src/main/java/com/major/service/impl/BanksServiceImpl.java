package com.major.service.impl;

import com.major.common.constant.Constants;
import com.major.entity.Banks;
import com.major.mapper.BanksMapper;
import com.major.model.request.BanksRequest;
import com.major.service.IBanksService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 银行表 服务实现类
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-09-20
 */
@Service
public class BanksServiceImpl extends ServiceImpl<BanksMapper, Banks> implements IBanksService {


    @Override
    public Page<Map<String, Object>> selectBanksPage(Page<Map<String, Object>> page, String bankName) {
        return  page.setRecords(baseMapper.selectBanksPage(page,bankName));
    }

    @Override
    public boolean addBanks(BanksRequest banksRequest){
        Banks banks=new Banks();
        BeanUtils.copyProperties(banksRequest, banks);
        banks.setStatus(Constants.STATUS_NORMAL);
        banks.setCreateTime(new Date());
        return super.insert(banks);
    }

    @Override
    public boolean updateBanks(BanksRequest banksRequest,Long id){
        Banks banks=new Banks();
        BeanUtils.copyProperties(banksRequest, banks);
        banks.setId(id);
        return super.updateById(banks);
    }

    @Override
    public boolean deleteBanks(Long id){
        Banks banks=new Banks();
        banks.setId(id);
        banks.setStatus(Constants.STATUS_DELETE);
        return super.updateById(banks);
    }

    @Override
    public Map<String, Object> selectBanksList() {
        Map<String, Object> map=new HashMap<>(1);
        map.put("banks_info",baseMapper.selectBanksList());
        return  map;
    }

}
