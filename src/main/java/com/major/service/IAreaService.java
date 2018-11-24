package com.major.service;


import com.major.entity.Area;
import com.major.model.request.AreaRequest;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.Map;

/**
 * <p>Title: 区域表数据服务层接口 </p>
 * <p>Description: Function Description </p>
 * <p>Copyright: Copyright (c) 2018  </p>
 * <p>Company: AG Co., Ltd.             </p>
 * <p>Create Time: 2018/7/13 10:35      </p>
 *
 * @author LianGuoQing
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
public interface IAreaService extends IService<Area> {

    /**
     * 模糊查询城市
     * @return
     */
    Map<String, Object> selectAreaByName(String name);

    /**
     * 查询城市名
     * @param cityId
     * @return
     */
    String getCityName(Long cityId);

    /**
     * 区域分页
     * @param page
     * @param parentName
     * @param name
     * @param areaType
     * @return
     */
    Page<Map<String, Object>> selectAreaPage(Page<Map<String, Object>> page, String parentName, String name, Integer areaType);

    /**
     * 新增
     * @param areaRequest
     * @return
     */
    boolean addArea(AreaRequest areaRequest);

    /**
     * 修改
     * @param areaRequest
     * @param id
     * @return
     */
    boolean updateArea(AreaRequest areaRequest ,Long id);

    /**
     * 删除
     * @param id
     * @return
     */
    boolean deleteArea(Long id);

    Map<String,Object> selectAreaById(Long id);
}
