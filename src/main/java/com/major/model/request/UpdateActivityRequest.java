package com.major.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

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
public class UpdateActivityRequest {

    @ApiModelProperty(value = "活动商品数量", required = true)
    @NotNull(message = "total_count不能为空")
    @Min(message = "total_count值最低1", value = 1L)
    @JsonProperty("total_count")
    private Long totalCount;

    @ApiModelProperty(value = "活动开始时间", required = true)
    @NotNull(message = "start_time不能为空")
    @JsonProperty("start_time")
    private Date startTime;

    @ApiModelProperty(value = "活动结束时间", required = true)
    @NotNull(message = "end_time不能为空")
    @JsonProperty("end_time")
    private Date endTime;

    @ApiModelProperty(value = "秒杀价格", required = true)
    @NotNull(message = "秒杀价格不能为空")
    private BigDecimal secKillPrice;

    /**
     * 每人秒杀限制数量
     */
    @ApiModelProperty(value = "每人秒杀限制数量", required = true)
    @NotNull(message = "secKillLimitNum不能为空")
    private Integer secKillLimitNum;

    @ApiModelProperty(value = "活动原价", required = true)
    @NotNull(message = "活动原价不能为空")
    private BigDecimal originalPrice;

    /**
     * 活动类型：0-秒杀；1-团购;2-砍价；3:-今日特惠
     */
    @ApiModelProperty(value = "活动类型", required = true)
    @NotNull(message = "activityType不能为空")
    private Integer activityType;

}
