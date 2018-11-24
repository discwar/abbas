package com.major.service;

import com.major.entity.ImgLog;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 图片记录表 服务类
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-09-13
 */
public interface IImgLogService extends IService<ImgLog> {

    /**
     * 批量添加
     * @param
     * @return
     */
    boolean addImgLogList(List<String>imgUrls,String fromTable,Long fromId,String fromField,Long operId);

    /**
     * 单条添加
     * @param
     * @return
     */
    boolean addImgLog(String imgUrl,String fromTable,Long fromId,String fromField,Long operId);

    /**
     * 针对修改操作
     * 批量删除oss上图片和数据库中数据
     * @param imgUrls 原图片地址
     * @param fromTable
     * @param fromId
     * @param reqImgUrls 新传入的图片地址
     * @return
     */
    boolean updateImgLogsByTableAndDelect(String imgUrls,String fromTable,Long fromId,String reqImgUrls,String fromField);

    /**
     * 图片记录分页列表
     * @param page
     * @param fromTable
     * @param operType
     * @return
     */
    Page<Map<String, Object>> selectImgLogPage(Page<Map<String, Object>> page, String fromTable,String fromField, Integer operType);

    /**
     * 针对删除操作
     * @param imgUrls
     * @param fromTable
     * @param fromId
     * @return
     */
    boolean deleteImgLogsByTable(String imgUrls,String fromTable,Long fromId,Long operId,String fromField);

    /**
     * 修改
     * @param imgUrls
     * @param fromTable
     * @param fromId
     * @param reqImgUrls
     * @param fromField
     * @return
     */
    boolean updateImgLogsByTable(String imgUrls,String fromTable,Long fromId,String reqImgUrls,String fromField);
}
