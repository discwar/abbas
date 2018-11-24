package com.major.common.enums;

import lombok.Getter;

/**
 * <p>Title: 账单类型枚举类 </p>
 * <p>Description: Function Description </p>
 * <p>Copyright: Copyright (c) 2018  </p>
 * <p>Company: AG Co., Ltd.             </p>
 * <p>Create Time: 2018/7/12 11:56      </p>
 *
 * @author LianGuoQing
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
@Getter
public enum BillTypeEnum {

    /**
     * 类型
     */
    ORDER_PAY(1, "订单支付"),
    WALLET_STORE(2, "钱包储值"),
    MAKER_STORE(3, "创客储值"),
    INVITE_MAKER(4, "邀请新用户成为创客"),
    CONSUME_REBATE(5, "好友消费返利"),
    WITHDRAW(6, "提现"),
    REFUND(7, "退款");

    private Integer value;
    private String desc;

    BillTypeEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static BillTypeEnum getBillTypeEnum(Integer value) {
        for (BillTypeEnum skipTypeEnum : BillTypeEnum.values()) {
            if (skipTypeEnum.getValue().equals(value)) {
                return skipTypeEnum;
            }
        }

        return null;
    }

}
