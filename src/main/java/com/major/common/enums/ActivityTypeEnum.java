package com.major.common.enums;


import com.major.common.exception.AgException;
import lombok.Getter;

/**
 * <p>Title: 活动类型枚举类 </p>
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
public enum ActivityTypeEnum {

    /**
     * 活动类型
     */
    SEC_KILL(0, "秒杀"),
    GROUP_BUYING(1, "团购"),
    BARGAIN(2, "砍价"),
    TODAY_DISCOUNT(3, "今日特惠");

    private Integer value;
    private String desc;

    ActivityTypeEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static ActivityTypeEnum getActivityTypeEnum(Integer value) {
        for (ActivityTypeEnum activityTypeEnum : ActivityTypeEnum.values()) {
            if (activityTypeEnum.getValue().equals(value)) {
                return activityTypeEnum;
            }
        }

        throw new AgException(StatusResultEnum.REQUIRE_ARGUMENT_VALID_FAIL, "activity_type值不在取值范围内！");
    }

}
