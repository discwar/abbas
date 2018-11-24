package com.major.service.impl;

import com.major.common.constant.Constants;
import com.major.common.constant.ImgLogConstants;
import com.major.common.enums.StatusResultEnum;
import com.major.common.exception.AgException;
import com.major.entity.Tips;
import com.major.mapper.TipsMapper;
import com.major.model.request.TipsRequest;
import com.major.service.IImgLogService;
import com.major.service.ITipsService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
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
public class TipsServiceImpl extends ServiceImpl<TipsMapper, Tips> implements ITipsService {
    @Autowired
    private IImgLogService imgLogService;
    @Override
    public Tips getTips(Long id) {
        EntityWrapper<Tips> query = new EntityWrapper<>();
        query.where("status={0}", Constants.STATUS_ENABLE)
                .and("id={0}", id);
        return this.selectOne(query);
    }

    @Override
    public Page<Map<String, Object>> selectTipsPage(Page<Map<String, Object>> page,String title,String startTime,
                                                    String endTime,Integer categoryId,Integer status) {
        return page.setRecords(baseMapper.selectTipsList(page, status, title,categoryId,startTime,endTime));
    }
    @Override
    public boolean saveTips(TipsRequest tipsRequest,Long operId) {
        if(tipsRequest.getCoverUrl()!=null && tipsRequest.getCoverUrl().length()>Constants.DB_FIELD_512 ) {
            throw new AgException(StatusResultEnum.DB_SAVE_ERROR,"文章封面地址过长");
        }
        Tips tips=new Tips();
        BeanUtils.copyProperties(tipsRequest, tips);
        tips.setStatus(Constants.STATUS_NORMAL);
         super.insert(tips);
         if(StringUtils.isNotEmpty(tipsRequest.getCoverUrl())){
             imgLogService.addImgLog(tipsRequest.getCoverUrl(), ImgLogConstants.FROM_TABLE_TIPS,tips.getId(),ImgLogConstants.FROM_FIELD_COVER_URL,operId);
         }
         return true;
    }

    @Override
    public boolean putTips(TipsRequest tipsRequest,Long id,Long operId) {
        if(tipsRequest.getCoverUrl()!=null && tipsRequest.getCoverUrl().length()>Constants.DB_FIELD_512 ) {
            throw new AgException(StatusResultEnum.DB_SAVE_ERROR,"文章封面地址过长");
        }
        Tips tips=selectById(id);
        if(StringUtils.isNotEmpty(tipsRequest.getCoverUrl())){
           imgLogService.updateImgLogsByTable(tips.getCoverUrl(),ImgLogConstants.FROM_TABLE_TIPS,id,tipsRequest.getCoverUrl(),ImgLogConstants.FROM_FIELD_COVER_URL);
        }
        BeanUtils.copyProperties(tipsRequest, tips);
        tips.setId(id);
        return super.updateById(tips);
    }

    @Override
    public boolean deleteTipsById(Long id,Long operId) {
        Tips tips=selectById(id);
        tips.setId(id);
        tips.setStatus(Constants.STATUS_DELETE);
        if(StringUtils.isNotEmpty(tips.getCoverUrl())){
            imgLogService.deleteImgLogsByTable(tips.getCoverUrl(),ImgLogConstants.FROM_TABLE_TIPS,id,operId,ImgLogConstants.FROM_FIELD_COVER_URL);
        }
        return tips.updateById();
    }

    @Override
    public Page<Map<String, Object>> selectTipsListByTitle(Page<Map<String, Object>> page,String title) {
      return page.setRecords(baseMapper.selectTipsListByTitle(page,title));
    }

    @Override
    public boolean shelvesTips(Long tipsId,Integer status) {
        Tips tips=new Tips();
        tips.setId(tipsId);
        tips.setStatus(status);
        return tips.updateById();
    }

    @Override
    public  Map<String, Object> selectTipsById(Long Id) {
        Map<String, Object> map=new HashMap<>();
        map.put("tip_list",baseMapper.selectTipsById(Id));
        return map;
    }

    @Override
   public  boolean removeTipsCategoryId(List<Tips> tipsList){
        for(Tips tips :tipsList){
            baseMapper.removeTipsCategoryId(tips.getId());
        }
        return true;
   }
}
