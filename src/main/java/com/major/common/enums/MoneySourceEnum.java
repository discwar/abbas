package com.major.common.enums;

import lombok.Getter;

/**
 * @author xuquanming
 * @date 2018/11/20 10:24
 */
@Getter
public enum MoneySourceEnum {
    /**
     * 钱包支付时，金钱来源
     */
    MAKER_STORED(0,"创客储值金"),
    WALLET(1,"钱包"),
    MAKER_INCOME(2,"创客收益金");
    private Integer value;
    private String desc;

    MoneySourceEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }


}
