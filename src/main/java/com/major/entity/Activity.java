package com.major.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>Title: Module Information         </p>
 * <p>Description: Function Description </p>
 * <p>Copyright: Copyright (c) 2018  </p>
 * <p>Company: AG Co., Ltd.             </p>
 * <p>Create Time: 2018/7/13 9:44      </p>
 *
 * @author LianGuoQing
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
@Getter
@Setter
@ToString
@TableName("activity")
public class Activity extends SuperEntity<Activity> {


    private Long goodsId;

    private BigDecimal originalPrice;

    /**
     * 秒杀价格/今日特惠/砍价价格
     */
    private BigDecimal secKillPrice;

    /**
     * 几人团价格，比如5-59表示5人团59元，多个用英文逗号隔开。砍价人数-砍一刀:比如5-1.25
     */
    private String groupBuying;

    /**
     * 活动说明
     */
    private String activityDesc;

    /**
     * 活动开始时间
     */
    private Date startTime;

    /**
     * 活动结束时间
     */
    private Date endTime;

    /**
     * 活动商品数量
     */
    private Long totalCount;

    /**
     * 活动销售数量
     */
    private Long ordersCount;

    /**
     * 每人秒杀限制数量
     */
    private Integer secKillLimitNum;

    /**
     * 活动类型：0-秒杀；1-团购;2-砍价；3:-今日特惠
     */
    private Integer activityType;

    /**
     * 活动状态：0-删除；1-上架；2-下架
     */
    private Integer status;



}
