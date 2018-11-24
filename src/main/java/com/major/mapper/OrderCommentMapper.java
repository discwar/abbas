package com.major.mapper;

import com.major.entity.OrderComment;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-08-17
 */
public interface OrderCommentMapper extends BaseMapper<OrderComment> {

    /**
     * 通用于商家自己查看评论和总部管理员点击某个店铺查看评论
     * @param page
     * @param shopId
     * @param sysUserId
     * @param orderNo
     * @param userName
     * @param comment
     * @param isShopReply
     * @param overallScoreStart
     * @param overallScoreStop
     * @param createTimeStart
     * @param createTimeStop
     * @return
     */
    List<Map<String, Object>> selectOrderCommentPage(Pagination page, @Param("shopId") Long shopId,
                                                     @Param("sysUserId") Long sysUserId,
                                                      @Param("orderNo") String orderNo, @Param("userName") String userName,
                                                      @Param("comment") String comment, @Param("isShopReply") Integer isShopReply,
                                                      @Param("overallScoreStart") String overallScoreStart, @Param("overallScoreStop") String overallScoreStop,
                                                      @Param("createTimeStart") String createTimeStart, @Param("createTimeStop") String createTimeStop);

   @Select("SELECT " +
           "oc.id,oc.create_time,o.order_no, " +
           "oc.user_name,oc.`comment`,oc.overall_score, " +
           "oc.taste_score,oc.package_score,oc.transporter_score,oc.img_urls ,oc.shop_reply,s.shop_name " +
           "FROM " +
           "order_comment oc " +
           "LEFT JOIN `order` o ON oc.order_id = o.id " +
           "left join shop  s  ON s.id=o.shop_id  " +
           "WHERE " +
           "oc.id=#{commentId}")
    Map<String,Object> selectOrderCommentById(@Param("commentId") Long commentId);


    /**
     * 查看总部自营店的评论
     * @param page
     * @param orderNo
     * @param userName
     * @param comment
     * @param isShopReply
     * @param overallScoreStart
     * @param overallScoreStop
     * @param createTimeStart
     * @param createTimeStop
     * @return
     */
    List<Map<String, Object>> selectAGOrderCommentPage(Pagination page,
                                                     @Param("orderNo") String orderNo, @Param("userName") String userName,
                                                     @Param("comment") String comment, @Param("isShopReply") Integer isShopReply,
                                                     @Param("overallScoreStart") String overallScoreStart, @Param("overallScoreStop") String overallScoreStop,
                                                     @Param("createTimeStart") String createTimeStart, @Param("createTimeStop") String createTimeStop);

}
