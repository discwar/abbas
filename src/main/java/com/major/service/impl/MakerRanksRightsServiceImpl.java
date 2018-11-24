package com.major.service.impl;

import com.major.entity.MakerRanksRights;
import com.major.mapper.MakerRanksRightsMapper;
import com.major.model.request.MakerRanksRightsRequest;
import com.major.model.request.UpdateMakerRanksRightsRequest;
import com.major.service.IMakerRanksRightsService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


/**
 * <p>
 * 创客等级与其专属权益关联表 服务实现类
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-11-20
 */
@Service
public class MakerRanksRightsServiceImpl extends ServiceImpl<MakerRanksRightsMapper, MakerRanksRights> implements IMakerRanksRightsService {

    @Override
    public boolean addMakerRanksRights(MakerRanksRightsRequest makerRanksRightsRequest){
        MakerRanksRights makerRanksRights=new MakerRanksRights();
        BeanUtils.copyProperties(makerRanksRightsRequest, makerRanksRights);
        return super.insert(makerRanksRights);
    }

    @Override
    public boolean updateMakerRanksRights(UpdateMakerRanksRightsRequest updateMakerRanksRightsRequest, Long id){
        MakerRanksRights makerRanksRights=selectById(id);
        BeanUtils.copyProperties(updateMakerRanksRightsRequest, makerRanksRights);
        return super.updateById(makerRanksRights);
    }

    @Override
    public Map<String,Object> selectMakerRanksRightsById( Long id){
        MakerRanksRights makerRanksRights=selectById(id);
        Map<String,Object> map=new HashMap<>(1);
        map.put("maker_ranks_rights_info",makerRanksRights);
        return map;
    }


}
