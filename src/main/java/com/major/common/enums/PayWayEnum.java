package com.major.common.enums;

import lombok.Getter;

/**
 * <p>Title: 支付方式枚举类 </p>
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
public enum PayWayEnum {

    /**
     * 类型
     */
    WALLET(1, "钱包"),
    ALIPAY(2, "支付宝"),
    WECHAT(3, "微信"),
    BANK_CARD(4, "银行卡");

    private Integer value;
    private String desc;

    PayWayEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static PayWayEnum getPayWayEnum(Integer value) {
        for (PayWayEnum skipTypeEnum : PayWayEnum.values()) {
            if (skipTypeEnum.getValue().equals(value)) {
                return skipTypeEnum;
            }
        }

        return null;
    }

}
