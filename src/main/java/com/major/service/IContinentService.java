package com.major.service;

import com.major.entity.Continent;
import com.baomidou.mybatisplus.service.IService;

import java.util.Map;

/**
 * <p>
 * 大洲表 服务类
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-10-24
 */
public interface IContinentService extends IService<Continent> {

    /**
     * 获取所有的洲
     * @return
     */
    Map<String,Object> selectContinent();

}
