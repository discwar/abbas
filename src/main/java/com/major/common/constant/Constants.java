package com.major.common.constant;

/**
 * 通用常量信息
 * @author LianGuoQing
 */
public interface Constants {

    /**
     * 状态：删除
     */
    Integer STATUS_DELETE = 0;
    /**
     * 状态：正常
     */
    Integer STATUS_NORMAL = 1;

    /**
     * 状态：禁用
     */
    Integer STATUS_DISABLE = 2;

    /**
     * 状态：暂停
     */
    Integer STATUS_PAUSE = 2;

    /**
     * 状态：启用、上架
     */
    Integer STATUS_ENABLE = 1;
    /**
     * 状态：下架
     */
    Integer STATUS_DOWN = 2;

    /**
     * 适用范围：全国通用
     */
    Integer SCOPE_ALL = 0;

    /**
     * 优惠劵状态：0-未使用；1-已使用；2-已过期
     */
    Integer COUPON_STATUS_USABLE = 0;
    Integer COUPON_STATUS_USED = 1;
    Integer COUPON_STATUS_OVERDUE = 2;

    /**
     * 优惠券获取方式：0-系统发放；1-签到；2-积分兑换；3-店内领取
     */
    Integer COUPON_SOUTCE_SYSTEM=0;

    /**
     * 消息状态：1-已发送；2-预定中；3-发送失败
     */
    Integer MSG_SENT = 1;
    Integer MSG_YD = 2;
    Integer MSG_FAIL =3;

    /**
     * 消息发送类型 1-立即发送；0-预约发送；2-定时器任务
     */
    Integer MSG_TYPE_NOW=1;
    Integer MSG_TYPE_WAIT=0;
    Integer MSG_TYPE_JOB=2;

    /**
     * 交易账单标识：1-支出；2-收入
     */
    Integer MARK_PAY = 1;
    Integer MARK_INCOME = 2;

    /**
     * UTF-8 字符集
     */
    String UTF8 = "UTF-8";

    /**
     * 通用成功标识
     */
    String SUCCESS = "0";
    /**
     * 通用失败标识
     */
    String FAIL = "1";

    /**
     * 登录成功
     */
    String LOGIN_SUCCESS = "Success";

    /**
     * 注销
     */
    String LOGOUT = "Logout";

    /**
     * 登录失败
     */
    String LOGIN_FAIL = "Error";

    /**
     * 表示爱果小店是否需要关联店铺 0-否 1-是
     */
    Integer SHOP_ISRELATION_TRUE=0;
    Integer SHOP_ISRELATION_FALSE=1;

    /**
     * 店铺是否已经关联爱果小店 是否关联过爱果小店:0-否；1-是
     */
     Integer SHOP_IS_RELATION_TYPE_FALSE=0;
    Integer SHOP_IS_RELATION_TYPE_TRUE=1;

    /**
     * 总部自营id
     */
    Long SHOP_ZY_ID=1L;
    /**
     * 表示爱果小店类型
     */
    Integer SHOP_TYPE_AG = 1;

    String SHOP_NAME_AG="爱果小店NO";

    String MSG_SUCCESS_SIZE="消息发送成功,已通知";

    String MSG_YU_SUCCESS="消息预约成功";

    /**
     * 所属对象类型：0-用户；1-商家；-1-系统
     */
    Integer TARGET_TYPE_USER = 0;
    Integer TARGET_TYPE_BUSINESS = 1;
    Integer TARGET_TYPE_SYSTEM = -1;


    String COUPON_SUCCESS_SIZE="优惠券发送成功,已通知";
    /**
     * 针对创建用户分组时的
     * 筛选状态:0-xx刚注册的用户;1-xx消费超过x元的用户;2-xx消费未超过x元的用户;3-24内未活跃的用户;4-全部用户
     */
      int SEARCH_TYPE_ZHU_CE=0;
      int SEARCH_TYPE_XIAO_FEI=1;
      int SEARCH_TYPE_WEI_XIAO_FEI=2;
      int SEARCH_TYPE_WEI_HUO_YUE=3;
      int SEARCH_TYPE_ALL=4;

    /**
     *优惠券种类：0-爱果券；1-店铺券
     */
    Integer COUPON_KIND_ZB=0;
    Integer COUPON_KIND_BUSINESS=1;

    /**
     * 优惠券类型：0-满减券；1-折扣券；2-运费券
     */
    Integer COUPON_TYPE_MJ=0;
    Integer COUPON_TYPE_ZK=1;
    Integer COUPON_TYPE_YF=2;


    String COUPON_DESC_SY="适用于";
    /**
     * 退款类型：0-退全部；1-退部分
     */
    Integer REFUND_TYPE_BF=0;
    Integer REFUND_TYPE_ALL = 1;

    /**
     *创客权益
     */
    String MARKER_RIGHTS_STORE = "store";

    /**
     * 是否返利 1 ：已返利
     */
    Integer  IS_REBATE = 1;

    /**
     * 配送方式：0-爱果配送；1-商家配送
     */
    Integer DELIVERY_TYPE_AG=0;
    Integer DELIVERY_TYPE_BN=1;

    /**
     * 创客储值优惠卷过期天数
     */
    Integer MAKER_STORAGE_COUPON_PAST = 7;

    /**
     * 商品优惠类型：0-特惠；1-折扣；2-普通；3:一元购
     */
    Integer PREFERENCE = 0;
    Integer DISCOUNT = 1;
    Integer ORDINARY = 2;
    Integer ONE_YUAN = 3;
    Integer PROMOTION_SEC_KILL = 4;

    /**
     *banner类型：1-首页；2-附件水果；3-采摘园；4-农场；5-进口水果；6-扶贫
     */
    Integer BANNER_TYPE_SY=1;

    /**
     * 跳转类型:0-不跳转 1-商品；2-店铺；3-文章；4-链接
     */
    Integer SKIP_TYPE_NO=0;
    Integer SKIP_TYPE_SP=1;
    Integer SKIP_TYPE_DP=2;

    /**
     * 店铺类型 2-水果店
     */
    Integer SHOP_TYPE_NEAR_FRUIT=2;

    /**
     * 统类型 0-iOS；1-Android；2-H5
     */
    Integer OS_TYPE_IOS=0;
    Integer OS_TYPE_ANDROID=1;
    Integer OS_TYPE_H5=2;

    /**
     * 提现状态：-1-提现失败；0-待处理；1-提现成功   cash_status
     */
    Integer CASH_STATUS_FAIL=-1;
    Integer CASH_STATUS_WAIT=0;
    Integer CASH_STATUS_SUCCESS=1;

    /**
     * 0-否；1-是
     */
    Integer NO = 0;
    Integer YES = 1;

    String PLUCKING_TICKET="采摘套票";

    /**
     * 任务类型：0-临时（只跑一次）；1-永久（跑多次）
     */
    Integer SYS_JOB_TYPE_TEMPORARY=0;
    Integer SYS_JOB_TYPE_PERMANENT=1;


    /**
     * 商品redisMapKey
     */
    String GOODS_TOTAL_COUNT = "total_count";
    String GOODS_PRICE = "current_price";
    String GOODS_LIMIT_NUM = "limit_num";
    String DELIVERY_TYPE = "delivery_type";
    String PROMOTION_TYPE = "promotion_type";


    /**
     * 商品的状态
     */
     Integer ORDINARY_GOODS = 0;
     Integer ACTIVITY_GOODS = 1;

    /**
     * 商品增减状态
     */
     Integer GOODS_NUM_ADD = 1;
     Integer GOODS_NUM_SUB = 0;

    /**
     * 适用范围：0-全国通用；1-多地市；2-单地市（附近水果仅适用于单地市）
     */
    Integer SCOPE_GENERAL=0;
    Integer SCOPE_SINGLE_CITIES=1;
    Integer SCOPE_MANY_CITIES=2;

    /**
     * 0-已结束，1-进行中，2-等待中
     */
    Integer ACTIVITY_TYPE_END=0;
    Integer ACTIVITY_TYPE_ONGOING=1;
    Integer ACTIVITY_TYPE_WAIT=2;

    /**
     * 附近水果和爱果店铺订单预计送达时间（分钟）
     */
    String estimatedExpendTime="45";

    /**
     * 数据库字段长度
     */
    int DB_FIELD_512=512;
    int DB_FIELD_255=255;

    String COUPON_STORED_NAME="创客储值送好礼";

    /**
     * 结算类型：0-手工结算；1-即时结算
     */
    Integer  SETTLEMENT_TYPE_MANUAL=0;
    Integer  SETTLEMENT_TYPE_INSTANT=1;


}
