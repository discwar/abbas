package com.major.common.constant;

import lombok.Getter;

/**
 * <p>Title: Module Information         </p>
 * <p>Description: Function Description </p>
 * <p>Copyright: Copyright (c) 2018  </p>
 * <p>Company: AG Co., Ltd.             </p>
 * <p>Create Time: 2018/8/17 17:46      </p>
 *
 * @author LianGuoQing
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
public interface OrderConstants {

    /**
     * 是否订单已支付：0-未支付；1-已支付
     */
    Integer ORDER_UNPAID = 0;
    Integer ORDER_PAID = 1;

    /**
     * 是否订单已完结：0-未完结；1-已完结
     */
    Integer ORDER_UNFINISHED = 0;
    Integer ORDER_FINISHED = 1;

    /**
     *订单退款表:审核状态：0-待审核；1-审核通过；2-拒绝
     */
    Integer AUDIT_STATUS_SUCCESS = 1;
    Integer AUDIT_STATUS_REFUSE = 2;

    /**
     * 0-订单未退款,1-订单已退款
     */
    Integer ORDER_UN_RESUND = 0;
    Integer ORDER_YES_RESUND = 1;
    /**
     * 订单未返利
     */
    Integer ORDER_UNREBATE = 0;

    /**
     * 申请取消订单状态：0-待审批；1-审批通过；2-审批不通过
     */
    int APPLY_CANCEL_STATUS_SUCCESS=1;
    int APPLY_CANCEL_STATUS_REFUSE=2;

    @Getter
    enum OrderTypeEnum {
        /**
         * 订单类型
         */
        ALL(0, "全部"),
        STAY_PAY(1, "待付款"),
        STAY_DELIVERY(2, "待收货"),
        GROUP_BUYING(3, "团购"),
        BARGAIN(4, "砍价");

        private Integer value;
        private String desc;

        OrderTypeEnum(Integer value, String desc) {
            this.value = value;
            this.desc = desc;
        }

        public static OrderTypeEnum getOrderTypeEnum(Integer value) {
            for (OrderTypeEnum orderTypeEnum : OrderTypeEnum.values()) {
                if (orderTypeEnum.getValue().equals(value)) {
                    return orderTypeEnum;
                }
            }

            return null;
        }

    }

    @Getter
    enum OrderStatusTypeEnum {
        /**
         * 订单状态类型
         */
        NEAR_DELIVERY(0, "爱果店铺&附近水果店"),
        PLUCKING_GARDEN_TICKET(1, "采摘园套票"),
        PLUCKING_GARDEN_GROUP(2, "采摘园出团"),
        LOGISTICS(3, "采摘园直销&农场&进口水果&扶贫订单"),
        GROUP_BUYING(4, "团购"),
        BARGAIN(5, "砍价");

        private Integer value;
        private String desc;

        OrderStatusTypeEnum(Integer value, String desc) {
            this.value = value;
            this.desc = desc;
        }

        public static OrderStatusTypeEnum getorderStatusTypeEnum(Integer value) {
            for (OrderStatusTypeEnum orderStatusTypeEnum : OrderStatusTypeEnum.values()) {
                if (orderStatusTypeEnum.getValue().equals(value)) {
                    return orderStatusTypeEnum;
                }
            }

            return null;
        }
    }

    @Getter
    enum OrderStatusTakeoutEnum {
        /**
         * 订单状态：爱果店铺&附近水果店（外卖订单）
         */
        SUBMIT(0, "订单已提交"),
        STAY_PAY(1, "等待付款"),
        WAIT_FOR(2,"支付成功，等待商家接单"),
        PREPARING(3, "商品准备中"),
        DELIVERY(4, "商品配送中，请耐心等待"),
        COMPLETED(5, "已送达，订单完成"),
        CANCEL(6, "订单已取消"),
        OVERTIME_CANCEL(7, "订单支付超时，已取消"),
        REFUNDING(8, "退款申请中"),
        REFUNDED(9, "已售后退款"),
        REFUND_FAIL(10, "退款失败"),
        REFUND_REFUSE(11,"商家拒绝接单");
        private Integer value;
        private String desc;

        OrderStatusTakeoutEnum(Integer value, String desc) {
            this.value = value;
            this.desc = desc;
        }
        public static OrderStatusTakeoutEnum getorderStatusTypeEnum(Integer value) {
            for (OrderStatusTakeoutEnum orderStatusTakeoutEnum : OrderStatusTakeoutEnum.values()) {
                if (orderStatusTakeoutEnum.getValue().equals(value)) {
                    return orderStatusTakeoutEnum;
                }
            }

            return null;
        }
    }

    @Getter
    enum OrderStatusTicketEnum {
        /**
         * 订单状态：采摘园套票
         */
        SUBMIT(0, "订单已提交"),
        STAY_USE(1, "已出票，待使用"),
        USED(2, "套票已使用"),
        REFUND_TICKET(3, "已退票");

        private Integer value;
        private String desc;

        OrderStatusTicketEnum(Integer value, String desc) {
            this.value = value;
            this.desc = desc;
        }
    }

    @Getter
    enum OrderStatusGroupEnum {
        /**
         * 订单状态：采摘园出团
         */
        SUBMIT(0, "订单已提交"),
        STAY_PLAY(1, "参团成功，待出游"),
        REFUND_GROUP(2, "已退团"),
        DELAY(3, "出团时间已顺延"),
        COMPLETED(4, "已出团");

        private Integer value;
        private String desc;

        OrderStatusGroupEnum(Integer value, String desc) {
            this.value = value;
            this.desc = desc;
        }
    }

    @Getter
    enum OrderStatusLogisticsEnum {
        /**
         * 订单状态：采摘园直销&农场&进口水果&扶贫订单（物流快递）
         */
        SUBMIT(0, "订单已提交"),
        STAY_PAY(1, "等待付款"),
        PREPARING(2, "支付成功，商品准备中"),
        DELIVERY(3, "商家已发货"),
        TRANSPORTING(4, "商品运输中，请耐心等待"),
        COMPLETED(5, "订单已完成"),
        CANCEL(6, "订单已取消"),
        OVERTIME_CANCEL(7, "订单支付超时，已取消"),
        REFUNDING(8, "退款申请中"),
        REFUNDED(9, "已售后退款"),
        REFUND_FAIL(10, "退款失败");

        private Integer value;
        private String desc;

        OrderStatusLogisticsEnum(Integer value, String desc) {
            this.value = value;
            this.desc = desc;
        }
        public static OrderStatusLogisticsEnum getorderStatusTypeEnum(Integer value) {
            for (OrderStatusLogisticsEnum orderStatusLogisticsEnum : OrderStatusLogisticsEnum.values()) {
                if (orderStatusLogisticsEnum.getValue().equals(value)) {
                    return orderStatusLogisticsEnum;
                }
            }

            return null;
        }
    }

    @Getter
    enum OrderStatusGroupBuyingEnum {
        /**
         * 订单状态：拼团（外卖订单）
         */
        SUBMIT(0, "订单已提交"),
        GROUP_BUYING(1, "团购中"),
        GROUP_BUYING_FAIL(2, "拼团未成功，已退款"),
        QUIT(3, "已退出拼团"),
        PREPARING(4, "拼团成功，商品准备中"),
        DELIVERY(5, "商品配送中，请耐心等待"),
        COMPLETED(6, "订单已完成"),
        REFUNDING(7, "退款申请中"),
        REFUNDED(8, "已售后退款"),
        REFUND_FAIL(9, "退款失败");

        private Integer value;
        private String desc;

        OrderStatusGroupBuyingEnum(Integer value, String desc) {
            this.value = value;
            this.desc = desc;
        }
        public static OrderStatusGroupBuyingEnum getOrderStatusGroupBuyingEnum(Integer value) {
            for (OrderStatusGroupBuyingEnum orderStatusGroupBuyingEnum : OrderStatusGroupBuyingEnum.values()) {
                if (orderStatusGroupBuyingEnum.getValue().equals(value)) {
                    return orderStatusGroupBuyingEnum;
                }
            }

            return null;
        }
    }

    @Getter
    enum OrderStatusBargainEnum {
        /**
         * 订单状态：砍价（外卖订单）
         */
        SUBMIT(0, "订单已提交"),
        BARGAINING(1, "发起砍价中"),
        EXPIRED(2, "未支付，砍价已过期"),
        PREPARING(3, "支付成功，商品准备中"),
        DELIVERY(4, "商品配送中，请耐心等待"),
        COMPLETED(5, "已送达，订单完成"),
        REFUNDING(6, "退款申请中"),
        REFUNDED(7, "已售后退款"),
        REFUND_FAIL(8, "退款失败");

        private Integer value;
        private String desc;

        OrderStatusBargainEnum(Integer value, String desc) {
            this.value = value;
            this.desc = desc;
        }
    }

}
