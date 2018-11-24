package com.major.service.impl;

import com.major.entity.UserBankCard;
import com.major.mapper.UserBankCardMapper;
import com.major.service.IUserBankCardService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 用户关联银行卡表 服务实现类
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-10-24
 */
@Service
public class UserBankCardServiceImpl extends ServiceImpl<UserBankCardMapper, UserBankCard> implements IUserBankCardService {

    @Override
    public Page<Map<String, Object>> selectUserBankCardPageByUserId(Page<Map<String, Object>> page, Long userId,String cardholder,String bankName) {
        Wrapper ew = new EntityWrapper();
        ew.where("target_id={0}",userId);
        ew.and("target_type=0");
        Map<String,Object> map=new HashMap<>();
        map.put("cardholder",cardholder);
        map.put("bank_name",bankName);
        ew.allEq(map);
        return page.setRecords(baseMapper.selectUserBankCardPageByUserId(page,ew));
    }
}
