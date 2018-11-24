package com.major.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>Title: Module Information         </p>
 * <p>Description: Function Description </p>
 * <p>Copyright: Copyright (c) 2018  </p>
 * <p>Company: AG Co., Ltd.             </p>
 * <p>Create Time: 2018/8/3 10:13      </p>
 *
 * @author LianGuoQing
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
@Getter
@Setter
@ToString
public class OrderDetail extends Model<OrderDetail> {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 订单id
     */
    private Long orderId;
    /**
     * 商品id
     */
    private Long goodsId;
    /**
     * 商品购买数量
     */
    private Integer goodsNum;
    /**
     * 单个商品总价格
     */
    private BigDecimal currentTotalPrice;
    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 商品图地址
     */
    private String goodsCoverUrl;
    /**
     * 是否参与满减：0-不参与；1-参与
     */
    private Integer moneyOff;
    /**
     * 商品标识：0-特惠；1-折扣；2-普通（普通商品都参与满减）;3-1元购；4-秒杀；5-团购；6-砍价；7-今日特惠
     */
    private Integer promotionType;
    /**
     * 商品状态信息
     */
    private String goodsStatusMsg;

    /**
     * 活动商品数量
     */
    private Integer activityNum;

    /**
     * 状态：1-正常；2-禁用；0-删除
     */
    private Integer status;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
