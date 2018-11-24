package com.major.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>Title: Module Information         </p>
 * <p>Description: Function Description </p>
 * <p>Copyright: Copyright (c) 2018  </p>
 * <p>Company: AG Co., Ltd.             </p>
 * <p>Create Time: 2018/8/10 9:25      </p>
 *
 * @author LianGuoQing
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
@Getter
@Setter
public class ActivityRequest extends UpdateActivityRequest{

    private Long shopId;

    private Long goodsId;


    /**
     * 几人团价格，比如5-59表示5人团59元，多个用英文逗号隔开。砍价人数-砍一刀:比如5-1.25
     */
    @ApiModelProperty(value = "几人团价格，比如5-59表示5人团59元，多个用英文逗号隔开。砍价人数-砍一刀:比如5-1.25")
    private String groupBuying;

    /**
     * 活动说明
     */
    @ApiModelProperty(value = "活动说明")
    private String activityDesc;


    /**
     * 活动销售数量
     */
    @ApiModelProperty(value = "活动销售数量")
    private Long ordersCount;


}
