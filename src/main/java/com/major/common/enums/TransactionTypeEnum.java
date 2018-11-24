package com.major.common.enums;

import lombok.Getter;

/**
 * <p>Title: 交易类型枚举类 </p>
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
public enum TransactionTypeEnum {

    /**
     * 类型
     */
    WALLET_STORE(0, "钱包储值"),
    ONLINE_CONSUME(1, "线上订单"),
    UNDERLINE_PAY(2, "线下支付"),
    MAKER_STORE(3, "创客储值"),
    MAKER_UPGRADE(4, "创客升级"),
    INVITE_MAKER(5, "邀请新用户成为创客"),
    CONSUME_REBATE(6, "好友消费返利"),
    CONSUME2_REBATE(7, "二级好友消费返利"),
    WITHDRAW(8, "提现"),
    REFUND_ALL(9, "退全款"),
    REFUND_PART(10, "退部分款");


    private Integer value;
    private String desc;

    TransactionTypeEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static TransactionTypeEnum getTransactionTypeEnum(Integer value) {
        for (TransactionTypeEnum skipTypeEnum : TransactionTypeEnum.values()) {
            if (skipTypeEnum.getValue().equals(value)) {
                return skipTypeEnum;
            }
        }

        return null;
    }

}
