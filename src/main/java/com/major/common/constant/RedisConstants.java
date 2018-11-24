package com.major.common.constant;

/**
 * <p>Title: Module Information         </p>
 * <p>Description: Function Description </p>
 * <p>Copyright: Copyright (c) 2018  </p>
 * <p>Company: AG Co., Ltd.             </p>
 * <p>Create Time: 2018/8/10 11:23      </p>
 *
 * @author LianGuoQing
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
public interface RedisConstants {

    /**
     * 用户登录次数计数前缀
     * 命名规则：前缀+username (字符串，值为登录次数)
     */
    String AG_LOGIN_COUNT_PREFIX = "ag_login:sys_user:";

    /**
     * 用户登录是否被锁定前缀
     * 命名规则：前缀+username (字符串，值为LOCK)
     */
    String AG_LOCK_PREFIX = "ag_lock:sys_user:";

    /**
     * 秒杀活动库存前缀
     * 命名规则：前缀+goodsId (列表)
     */
    String SEC_KILL_STOCK_PREFIX = "sec_kill:stock:goods:";
    /**
     * 秒杀活动前缀
     * 命名规则：前缀+goodsId (哈希，活动信息键值对)
     */
    String SEC_KILL_ACTIVITY_PREFIX = "sec_kill:activity:goods:";

    /**
     * 系统配置前缀
     * 命名规则：前缀+osType (哈希，密钥规则键值对)
     */
    String SYS_CONFIG_PREFIX = "sc:sys_config:";

    /**
     * 达达城市代码
     */
    String CITY_CODE = "dada:city_code";

    /**
     * Redis商品库存前缀
     * 命名规则：前缀+shopId (哈希，goodsId:goodsNum键值对)
     */
    String GOODS_STOCK_PREFIX = "stock:goods:";

    /**
     * 创客排行榜（最多前50名）前缀
     * 命名规则：前缀+yyyy-MM (SortedSet有序集合，value为userID，score为收益金额)
     */
    String MAKER_RANKING_LIST_PREFIX = "maker:ranking:";

    /**
     * 创客信息前缀
     * 命名规则：前缀+userId (哈希，创客信息键值对，比如avatar、phone、ranks_name)
     */
    String MAKER_INFO_PREFIX = "maker:user:";

    /**
     * App分享配置前缀
     * 命名规则：前缀+shareType (哈希，键值对)
     */
    String APP_SHARE_CONFIG_PREFIX = "sc:app_share_config:";

    /**
     * 爱果有话说前5数据
     * 命名规则：前缀 (List列表，从左到右升序)
     */
    String AG_SAY_TOP = "last:ag_say";

    /**
     *系统动态配置
     * 命名规则：前缀+动态类型{dynamic_type}  (哈希储存)
     * key对应{keyword} 关键字
     * value对应map（dynamic_title  动态标题、dynamic_desc 动态描述）
     */
    String  DYNAMIC_CONFIG = "sc:dynamic_config:";
}
