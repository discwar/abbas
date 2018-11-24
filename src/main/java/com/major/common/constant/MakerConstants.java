package com.major.common.constant;

import lombok.Getter;

/**
 * @author xuquanming
 * @date 2018/11/1 21:41
 */
public interface MakerConstants {
    @Getter
    enum MakerRankEnum{
        /**
         * 创客等级枚举
         */
        BRONZE(1,"铜牌"),
        SILVER(2,"银牌"),
        GOLD(3,"金牌");
        private Integer value;
        private String desc;

        MakerRankEnum(Integer value, String desc) {
            this.value = value;
            this.desc = desc;
        }

    }

    @Getter
    enum MakerRightEnum{
        /**
         * 创客权益
         */
        CONSUME("consume","消费"),
        STORE("store","储值"),
        CONSUME2("consume2","二级消费");
        private String key;
        private String desc;

        MakerRightEnum(String key, String desc) {
            this.key = key;
            this.desc = desc;
        }
    }
}
