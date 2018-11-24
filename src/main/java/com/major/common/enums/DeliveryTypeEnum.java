package com.major.common.enums;


import com.major.common.exception.AgException;
import lombok.Getter;

/**
 * <p>Title: 配送方式枚举类 </p>
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
public enum DeliveryTypeEnum {

    /**
     * 配送方式
     */
    AG_DISTRIBUTION(0, "爱果配送"),
    SHOP_DISTRIBUTION(1, "商家配送"),
    LOGISTICS_DISTRIBUTION(2, "物流配送");

    private Integer value;
    private String desc;

    DeliveryTypeEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static DeliveryTypeEnum getDeliveryTypeEnum(Integer value) {
        for (DeliveryTypeEnum deliveryTypeEnum : DeliveryTypeEnum.values()) {
            if (deliveryTypeEnum.getValue().equals(value)) {
                return deliveryTypeEnum;
            }
        }

        throw new AgException(StatusResultEnum.REQUIRE_ARGUMENT_VALID_FAIL, "delivery_type值不在取值范围内！");
    }

}
