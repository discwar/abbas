package com.major.service;

import com.major.entity.Tips;
import com.major.model.request.TipsRequest;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
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
public interface ITipsService extends IService<Tips> {

    /**
     * 通过ID获取小贴士信息
     *
     * @param id
     * @return
     */
    Tips getTips(Long id);

    /**
     * 获取小贴士信息列表，分页显示
     *
     * @param page
     * @return
     */
    Page<Map<String, Object>> selectTipsPage(Page<Map<String, Object>> page, String title,String startTime,
                                             String endTime,Integer category,Integer status);

    /**
     * 添加文章
     * @param tipsRequest
     * @return
     */
    boolean saveTips(TipsRequest tipsRequest,Long operId);

    /**
     * 修改文章
     * @param tipsRequest
     * @param id
     * @return
     */
    boolean putTips(TipsRequest tipsRequest,Long id,Long operId);

    /**
     * 删除文章
     * @param id
     * @return
     */
    boolean deleteTipsById(Long id,Long operId);

    /**
     * 根据文章标题模糊查询返回数据
     * @param title
     * @return
     */
    Page<Map<String, Object>> selectTipsListByTitle(Page<Map<String, Object>> page,String title);

    /**
     * 小贴士上架-下架
     * @param tipsId
     * @param status
     * @return
     */
    boolean shelvesTips(Long tipsId,Integer status);

    /**
     * 获取当前小贴士
     * @param Id
     * @return
     */
    Map<String, Object> selectTipsById(Long Id);

    /**
     * 置空
     * @param tipsList
     * @return
     */
    boolean removeTipsCategoryId(List<Tips> tipsList);
}
