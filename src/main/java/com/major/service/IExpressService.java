package com.major.service;

import com.major.entity.Express;
import com.major.model.request.ExpressRequest;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 快递表 服务类
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-08-29
 */
public interface IExpressService extends IService<Express> {

    /**
     * 查询所有快递公司
     * @return
     */
    List<Express> selectExpressAll();

    /**
     * 获取所有快递公司分页列表
     * @param page
     * @param name
     * @return
     */
    Page<Map<String, Object>> selectExpressPage(Page<Map<String, Object>> page, String name);

    /**
     * 添加
     * @param expressRequest
     * @return
     */
    boolean addExpress(ExpressRequest expressRequest);

    /**
     * 修改
     * @param expressRequest
     * @param id
     * @return
     */
    boolean updateExpress(ExpressRequest expressRequest,Long id);

    /**
     * 删除
     * @param id
     * @return
     */
    boolean deleteExpress(Long id);

}
