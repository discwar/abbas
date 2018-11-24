package com.major.model.response;

import lombok.Data;

/**
 * @author xuquanming
 * @date 2018/9/10 16:33
 */

@Data
public class DaDaAddOrderResponse {

    /**
     * 配送距离(单位：米)
     */
    private Double distance;
    /**
     * 实际运费(单位：元)，运费减去优惠券费用
     */
    private Double fee;
    /**
     *运费(单位：元)
     */
    private Double deliverFee;
    /**
     *优惠券费用(单位：元)
     */
    private Double couponFee;
    /**
     *小费(单位：元)
     */
    private Double tips;
    /**
     *保价费(单位：元)
     */
    private Double insuranceFee;

}
