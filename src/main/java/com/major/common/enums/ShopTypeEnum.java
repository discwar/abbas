package com.major.common.enums;


import com.major.common.exception.AgException;
import lombok.Getter;

/**
 * <p>Title: 商店类型枚举类 </p>
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
public enum ShopTypeEnum {

    /**
     * 类型
     */
    SELF_SUPPORT(0, "总部自营"),
    AIGUO_SHOP(1, "爱果小店"),
    NEAR_FRUIT(2, "水果店"),
    FARM(3, "农场"),
    PLUCKING_GARDEN(4, "采摘园"),
    IMPORT_FRUIT(5, "进口商"),
    POVERTY_ALLEVIATION(6, "扶贫区"),

    /**
     * 优惠券新增适用范围
     */
    GIFT_BOX(7, "礼品果盒"),
    HOT_SALE(8, "当季热卖"),
    PRE_SALE(9, "下季预售"),
    ONE_DOLLAR_BUY(10,"一元购");
    private Integer value;
    private String desc;

    ShopTypeEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static ShopTypeEnum getShopTypeEnum(Integer value) {
        for (ShopTypeEnum shopTypeEnum : ShopTypeEnum.values()) {
            if (shopTypeEnum.getValue().equals(value)) {
                return shopTypeEnum;
            }
        }

        throw new AgException(StatusResultEnum.REQUIRE_ARGUMENT_VALID_FAIL, "shop_type值不在取值范围内！");
    }

}
