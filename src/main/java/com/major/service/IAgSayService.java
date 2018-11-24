package com.major.service;

import com.major.entity.AgSay;
import com.major.model.request.AgSayRequest;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.Map;

/**
 * <p>Title: Module Information         </p>
 * <p>Description: Function Description </p>
 * <p>Copyright: Copyright (c) 2018  </p>
 * <p>Company: AG Co., Ltd.             </p>
 * <p>Create Time: 2018/7/18 19:36      </p>
 *
 * @author LianGuoQing
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
public interface IAgSayService extends IService<AgSay> {


    boolean addAgSay(AgSayRequest agSayRequest);


    boolean updateAgSay(AgSayRequest agSayRequest, Long id);


    boolean deleteAgSay( Long userId);


    Page<Map<String, Object>> selectAgSay(Page<Map<String, Object>> page,String title,String createTimeStart,String createTimeStop,Integer status);


    AgSay selectAgSayById(Long Id);

    /**
     * 上架下架
     * @param agSaysId
     * @param status
     * @return
     */
    boolean shelvesAgSay(Long agSaysId,Integer status);
}
