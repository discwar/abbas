package com.major.service.impl;


import com.major.common.constant.Constants;
import com.major.common.util.ChineseCharToEnUtils;
import com.major.entity.Area;
import com.major.mapper.AreaMapper;
import com.major.model.request.AreaRequest;
import com.major.service.IAreaService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Title: 区域表数据服务层接口实现类 </p>
 * <p>Description: Function Description </p>
 * <p>Copyright: Copyright (c) 2018  </p>
 * <p>Company: AG Co., Ltd.             </p>
 * <p>Create Time: 2018/7/13 10:37      </p>
 *
 * @author LianGuoQing
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
@Service
public class AreaServiceImpl extends ServiceImpl<AreaMapper, Area> implements IAreaService {

    @Override
    public Map<String, Object> selectAreaByName(String name) {
        Map<String, Object> result = new HashMap<>();
        result.put("area_info",baseMapper.selectProvinceCityByName(name));
        return result;
    }

    @Override
    public String getCityName(Long cityId) {
        Area area = super.selectById(cityId);
        return area.getName();
    }

    @Override
    public Page<Map<String, Object>> selectAreaPage(Page<Map<String, Object>> page,  String parentName, String name,  Integer areaType) {
        Wrapper ew = new EntityWrapper();
        ew.where("a.status <> {0} ",Constants.STATUS_DELETE);
        Map<String,Object> map=new HashMap<>(1);
        map.put("a.area_type",areaType);
        if(StringUtils.isNotEmpty(parentName)){
            ew.like("b.`name`",parentName);
        }
        if(StringUtils.isNotEmpty(name)){
            ew.like("a.`name`",name);
        }
        ew.allEq(map);
        return  page.setRecords(baseMapper.selectAreaPage(page,ew));
    }

    @Override
    public boolean addArea(AreaRequest areaRequest) {
        Area area =new Area();
        BeanUtils.copyProperties(areaRequest, area);
        area.setAreaKey(ChineseCharToEnUtils.getFirstSpell(areaRequest.getName()));
        area.setStatus(Constants.STATUS_NORMAL);
        return insert(area);
    }

    @Override
    public boolean updateArea(AreaRequest areaRequest ,Long id) {
        Area area =selectById(id);
        BeanUtils.copyProperties(areaRequest, area);
        if(StringUtils.isNotEmpty(areaRequest.getName())){
            area.setAreaKey(ChineseCharToEnUtils.getFirstSpell(areaRequest.getName()));
        }
        return updateById(area);
    }

    @Override
    public boolean deleteArea(Long id){
        Area area =selectById(id);
        area.setId(id);
        area.setStatus(Constants.STATUS_DELETE);
        return updateById(area);
    }

    @Override
    public Map<String,Object> selectAreaById(Long id) {
        Map<String,Object> map=new HashMap<>(1);
        map.put("area_info",selectById(id));
      return map;
    }
}
