package com.major.model.merchant;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author: zhangzhenliang
 * https://poll.kuaidi100.com/pollquery/pollTechWord.jsp
 * 最新查询结果，若在订阅报文中通过interCom字段开通了国际版，则此lastResult表示出发国的查询结果，全量，倒序（即时间最新的在最前）
 * 部分字段
 * @Date: 2018/9/25 14:31
 * @Version 1.0
 */
@Getter
@Setter
public class LastResult {
    private String message;
    /**
     * 快递单当前签收状态，包括0在途中、1已揽收、2疑难、3已签收、4退签、5同城派送中、6退回、7转单等7个状态，其中4-7需要另外开通才有效
     */
    private String state;
    private String  com;
    private String nu;


}
