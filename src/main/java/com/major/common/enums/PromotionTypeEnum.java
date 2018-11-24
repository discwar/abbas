package com.major.common.enums;


import com.major.common.exception.AgException;
import lombok.Getter;

/**
 * <p>Title: 商品优惠类型枚举类 </p>
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
public enum PromotionTypeEnum {

    /**
     * 商品优惠类型
     */
    PREFERENCE(0, "特惠"),
    DISCOUNT(1, "折扣"),
    ORDINARY(2, "普通"),
    ONE_YUAN(3, "一元购"),
    SEC_KILL(4, "秒杀"),
    GROUP_BUYING(5, "团购"),
    BARGAIN(6, "砍价"),
    TODAY_DISCOUNT(7, "今日特惠");

    private Integer value;
    private String desc;

    PromotionTypeEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static PromotionTypeEnum getPromotionTypeEnum(Integer value) {
        for (PromotionTypeEnum promotionTypeEnum : PromotionTypeEnum.values()) {
            if (promotionTypeEnum.getValue().equals(value)) {
                return promotionTypeEnum;
            }
        }

        throw new AgException(StatusResultEnum.REQUIRE_ARGUMENT_VALID_FAIL, "promotion_type值不在取值范围内！");
    }

}
