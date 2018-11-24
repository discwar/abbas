package com.major.model.request;

import com.major.model.merchant.LastResult;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author: zhangzhenliang
 * @Date: 2018/9/25 10:41
 * @Version 1.0
 */
@Getter
@Setter
public class KuaiDi100NotifyRequest {

    /**
     * 监控状态:polling:监控中，shutdown:结束，abort:中止，updateall：重新推送。其中当快递单为已签收时status=shutdown，
     * 当message为“3天查询无记录”或“60天无变化时”status= abort ，对于stuatus=abort的状度，需要增加额外的处理逻辑
     */
    private String status;

    /**
     * 	监控状态相关消息，如:3天查询无记录，60天无变化
     */
    private String message;

    /**
     * 最新查询结果，若在订阅报文中通过interCom字段开通了国际版，则此lastResult表示出发国的查询结果，全量，倒序（即时间最新的在最前）
     */
    private LastResult lastResult;



}
