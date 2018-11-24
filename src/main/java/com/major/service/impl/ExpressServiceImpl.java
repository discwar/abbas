package com.major.service.impl;

import com.major.common.constant.Constants;
import com.major.entity.Express;
import com.major.mapper.ExpressMapper;
import com.major.model.request.ExpressRequest;
import com.major.service.IExpressService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 快递表 服务实现类
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-08-29
 */
@Service
public class ExpressServiceImpl extends ServiceImpl<ExpressMapper, Express> implements IExpressService {

    @Override
    public  List<Express> selectExpressAll(){
        return  baseMapper.selectExpressAll();
    }


    @Override
    public Page<Map<String, Object>> selectExpressPage(Page<Map<String, Object>> page, String name) {
        return  page.setRecords(baseMapper.selectExpressPage(page,name));
    }

    @Override
    public boolean addExpress(ExpressRequest expressRequest){
        Express express=new Express();
        BeanUtils.copyProperties(expressRequest, express);
        express.setStatus(Constants.STATUS_NORMAL);
        express.setCreateTime(new Date());
        return super.insert(express);
    }

    @Override
    public boolean updateExpress(ExpressRequest expressRequest,Long id){
        Express express=new Express();
        BeanUtils.copyProperties(expressRequest, express);
        express.setId(id);
        return super.updateById(express);
    }

    @Override
    public boolean deleteExpress(Long id){
        Express express=new Express();
        express.setId(id);
        express.setStatus(Constants.STATUS_DELETE);
        return super.updateById(express);
    }
}
