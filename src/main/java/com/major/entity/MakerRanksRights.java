package com.major.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * <p>
 * 创客等级与其专属权益关联表
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-11-20
 */
@TableName("maker_ranks_rights")
@Getter
@Setter
public class MakerRanksRights extends SuperEntity<MakerRanksRights> {

    private static final long serialVersionUID = 1L;
    /**
     * maker_ranks表ID
     */
    @TableField("maker_ranks_id")
    private Integer makerRanksId;
    /**
     * maker_rights表ID
     */
    @TableField("maker_rights_id")
    private Integer makerRightsId;
    /**
     * 创客专属权益描述
     */
    @TableField("rights_desc")
    private String rightsDesc;
    /**
     * 返现比例
     */
    @TableField("rebate_ratio")
    private BigDecimal rebateRatio;

}
