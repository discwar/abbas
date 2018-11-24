package com.major.common.constant;

import com.major.common.enums.StatusResultEnum;
import com.major.common.exception.AgException;
import lombok.Getter;

/**
 * @author xuquanming
 * @date 2018/11/16 14:09
 */
public interface DynamicConfigConstants {

    /**
     * 什么是店铺专客？
     */
    String ABOUT_SPECIAL_GUEST = "aboutSpecialGuest";

    /**
     * 创客收益排行榜提示语
     */
    String MAKER_RANK_TIPS = "makerRankTips";
    /**
     * 创客权益URL
     */
    String MAKER_RIGHTS_URL = "makerRightsUrl";
    /**
     * 创客储值金使用描述
     */
    String MAKER_AMOUNT_USE_DESC = "amountUseDesc";
    /**
     * 创客收益描述
     */
    String MAKER_INCOME_DESC = "makerIncomeDesc";
    /**
     * 创客储值描述
     */
    String MAKER_STORED_DESC = "makerStoredDesc";

    /**
     * 用户默认头像地址
     */
    String DEFAULT_AVATAR_URL = "defaultAvatarUrl";
    /**
     * 连续签到几天获得额外奖励
     */
    String CONTINUOUS_DAYS = "continuousDays";
    /**
     * 额外奖励的积分数
     */
    String ADDITIONAL_SCORE = "additionalScore";
    /**
     * 钱包储值描述
     */
    String WALLET_STORED_DESC = "walletStoredDesc";
    /**
     * 用户邀请失效时间（单位天）
     */
    String INVITATION_TIMEOUT = "invitationTimeout";

    /**
     * 未支付订单失效时间（单位分）
     */
    String UNPAID_TIMEOUT = "unpaidTimeout";
    /**
     * 允许退款有效天数（单位天）
     */
    String ALLOW_REFUND_DAY = "allowRefundDay";
    /**
     * 催单间隔时间（单位分）
     */
    String URGE_ORDER_INTERVAL = "urgeOrderInterval";
    /**
     * 订单消息通知服务
     */
    String ORDER_NOTIFY_URL = "orderNotifyUrl";
    /**
     * 线下订单图片地址
     */
    String ORDER_OFFLINE_IMG = "offlineOrderImg";
    /**
     * 采摘园套票商品默认封面
     */
    String ORDER_TICKET_IMG = "ticketOrderUrl";

    /**
     * 用户使用协议URL
     */
    String USER_AGREEMENT_URL = "userAgreementUrl";
    /**
     * 关于爱果URL
     */
    String ABOUT_AI_GUO_URL = "aboutAiGuoUrl";
    /**
     * 软件下载URL
     */
    String DOWNLOAD_URL = "downloadUrl";
    /**
     * 隐私协议URL
     */
    String PRIVACY_URL = "privacyUrl";
    /**
     * 客服电话
     */
    String SERVICE_TEL = "serviceTel";
    /**
     * 爱果APP密钥串
     */
    String APP_SECRET = "appSecret";
    /**
     * 助力扶贫URL
     */
    String POVERTY_ALLEVIATION_URL = "povertyAlleviationUrl";
    /**
     * 爱果LOGO地址
     */
    String AG_LOGO_URL = "agLogoUrl";

    String DYNAMIC_TITLE = "dynamic_title";
    String DYNAMIC_DESC = "dynamic_desc";

    @Getter
    enum DynamicTypeEnum {
        /**
         * 动态配置枚举
         */
        SPECIAL_GUEST(0,"专客"),
        MAKER(1,"创客"),
        USER(2,"用户"),
        ORDER(3,"订单"),
        SYS(4,"系统");

        private Integer value;
        private String desc;

        DynamicTypeEnum(Integer value, String desc) {
            this.value = value;
            this.desc = desc;
        }

        public static DynamicTypeEnum getDynamicTypeEnum(Integer value){
            for (DynamicTypeEnum dynamicTypeEnum : DynamicTypeEnum.values()){
                if (value.equals(dynamicTypeEnum.getValue())){
                    return dynamicTypeEnum;
                }
            }
            throw new AgException(StatusResultEnum.REQUIRE_ARGUMENT_VALID_FAIL,"dynamic_type在取值范围之内");
        }
    }
}


