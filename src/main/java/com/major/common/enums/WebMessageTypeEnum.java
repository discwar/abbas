package com.major.common.enums;

import lombok.Getter;

/**
 * <p>Title: Module Information         </p>
 * <p>Description: Function Description </p>
 * <p>Copyright: Copyright (c) 2018  </p>
 * <p>Company: AG Co., Ltd.             </p>
 * <p>Create Time: 2018/10/17 14:32      </p>
 *
 * @author LianGuoQing
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
@Getter
public enum WebMessageTypeEnum {

    /**
     * 0-商家接单通知；1-用户申请取消订单通知；2-用户申请订单退款通知；3-催单通知；4-订单配送异常通知；5-提现申请通知；
     */
    RECEIPT(0,"商家接单通知"),
    CANCEL(1,"用户申请取消订单通知"),
    REFUND(2,"用户申请订单退款通知"),
    REMINDER(3,"催单通知"),
    ABNORMAL(4,"订单配送异常通知");

    private Integer value;
    private String desc;

    WebMessageTypeEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static WebMessageTypeEnum getWebMessageTypeEnum(Integer value) {
        for (WebMessageTypeEnum webMessageTypeEnum : WebMessageTypeEnum.values()) {
            if (webMessageTypeEnum.getValue().equals(value)) {
                return webMessageTypeEnum;
            }
        }
        return null;
    }

}
