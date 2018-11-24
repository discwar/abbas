package com.major.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 * 动态配置表
 * </p>
 *
 * @author XuQuanMing
 * @since 2018-11-16
 */
@Getter
@Setter
@ToString
public class DynamicConfig extends SuperEntity<DynamicConfig> {

    /**
     * 动态标题
     */
    @TableField("dynamic_title")
    private String dynamicTitle;
    /**
     * 动态描述
     */
    @TableField("dynamic_desc")
    private String dynamicDesc;
    /**
     * 关键字
     */
    private String keyword;
    /**
     * 动态类型：0-专客；1-创客；2-用户；3-订单
     */
    @TableField("dynamic_type")
    private Integer dynamicType;
    /**
     * 状态：1-正常；2-禁用；0-删除
     */
    private Integer status;

}
