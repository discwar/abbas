package com.major.service.impl;

import com.major.common.constant.Constants;
import com.major.common.enums.StatusResultEnum;
import com.major.common.exception.AgException;
import com.major.entity.Tips;
import com.major.entity.TipsCategory;
import com.major.mapper.TipsCategoryMapper;
import com.major.model.request.TipsCategoryRequset;
import com.major.service.ITipsCategoryService;
import com.major.service.ITipsService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Title: Module Information         </p>
 * <p>Description: Function Description </p>
 * <p>Copyright: Copyright (c) 2018  </p>
 * <p>Company: AG Co., Ltd.             </p>
 * <p>Create Time: 2018/7/18 19:37      </p>
 *
 * @author LianGuoQing
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
@Service
public class TipsCategoryServiceImpl extends ServiceImpl<TipsCategoryMapper, TipsCategory> implements ITipsCategoryService {

    @Autowired
    private ITipsService tipsService;

    @Override
    public Page<Map<String, Object>> selectTipsCategoryPage(Page<Map<String, Object>> page){
        return page.setRecords(baseMapper.selectTipsCategoryPage(page));
    }

    @Override
    public Map<String, Object> getTipsCategoryList() {
        Map<String, Object> resultMap = new HashMap<>(1);
        List<Map<String, Object>> tipsCategoryList = baseMapper.selectTipsCategoryList();
        resultMap.put("tips_category_list", tipsCategoryList);
        return resultMap;
    }
    @Override
    public boolean saveTipsCategory(TipsCategoryRequset tipsCategoryRequset) {
        //校验分类是否重复
        List<Map<String, Object>> list=baseMapper.selectTipsCategoryByName(tipsCategoryRequset.getName());
        if(null!=list && list.size()>0){
            throw new AgException(StatusResultEnum.DB_SAVE_ERROR,"存在同名的分类名称");
        }
        TipsCategory tipsCategory=new TipsCategory();
        BeanUtils.copyProperties(tipsCategoryRequset, tipsCategory);
        tipsCategory.setStatus(Constants.STATUS_NORMAL);
        return super.insert(tipsCategory);
    }

    @Override
    public boolean putTipsCategory(TipsCategoryRequset tipsCategoryRequset,Long id) {
        //校验分类是否重复
        List<Map<String, Object>> list=baseMapper.selectTipsCategoryByName(tipsCategoryRequset.getName());
        if(null!=list && list.size()>0){
            throw new AgException(StatusResultEnum.DB_SAVE_ERROR,"存在同名的分类名称");
        }
        TipsCategory tipsCategory=new TipsCategory();
        BeanUtils.copyProperties(tipsCategoryRequset, tipsCategory);
        tipsCategory.setId(id);
        return super.updateById(tipsCategory);
    }

    @Override
    public boolean deleteTipsCategory(Long id) {
        //当删除小贴士分类时，置空该分类下的小贴士的分类id
        List<Tips> tipsList=baseMapper.selectTipsByCategoryId(id);
        if(tipsList!=null && tipsList.size()>0){
            tipsService.removeTipsCategoryId(tipsList);
        }
        TipsCategory tipsCategory=new TipsCategory();
        tipsCategory.setId(id);
        tipsCategory.setStatus(Constants.STATUS_DELETE);
        return tipsCategory.updateById();
    }

    @Override
    public boolean shelvesTipsCategory(Long id,Integer status){
        TipsCategory tipsCategory=new TipsCategory();
        tipsCategory.setId(id);
        tipsCategory.setStatus(status);
        return tipsCategory.updateById();
    }

}
