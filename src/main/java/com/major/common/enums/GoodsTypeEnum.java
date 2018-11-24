package com.major.common.enums;

import lombok.Getter;

/**
 * <p>Title: 商品类型枚举类 </p>
 * <p>Description: Function Description </p>
 * <p>Copyright: Copyright (c) 2018  </p>
 * <p>Company: AG Co., Ltd.             </p>
 * <p>Create Time: 2018/9/7 11:56      </p>
 *
 * @author LianGuoQing
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
@Getter
public enum GoodsTypeEnum {

    /**
     * 0-普通（店铺类型为：2-水果店；3-农场；4-采摘园；5-进口商）；
     * 1-果切果汁（店铺类型为：爱果小店）；
     * 2-礼品果盒（总部自营店）；
     * 3-当季热卖（总部自营店）；
     * 4-预售（总部自营店）；
     * 5-1元购（总部自营店）
     */
    COMMON(0, "普通"),
    FRUIT_JUICE(1, "果切果汁"),
    GIFT_BOX(2, "礼品果盒"),
    SEASON_HOT(3, "当季热卖"),
    PRE_SALE(4, "下季预售"),
    ONE_DOLLAR_BUY(5, "一元购");

    private Integer value;
    private String desc;

    GoodsTypeEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static GoodsTypeEnum getGoodsTypeEnum(Integer value) {
        for (GoodsTypeEnum goodsTypeEnum : GoodsTypeEnum.values()) {
            if (goodsTypeEnum.getValue().equals(value)) {
                return goodsTypeEnum;
            }
        }
        return null;
    }
}
