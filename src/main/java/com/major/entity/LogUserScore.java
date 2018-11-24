package com.major.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户积分记录表
 * </p>
 *
 * @author LianGuoQing
 * @since 2018-08-17
 */
@Getter
@Setter
@ToString
public class LogUserScore extends Model<LogUserScore> {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;
    @JsonIgnore
    @JSONField(serialize = false)
    private Long userId;
    /**
     * 获得积分数
     */
    private Integer score;
    /**
     * 积分来源：0-积分兑换；1-线上订单消费；2-线下扫码支付；3-储值成为创客；4-储值升级创客；5-邀请好友注册；6-邀请的好友成为创客；7-签到；8-阅读小贴士；9-评价订单；10-分享App；11-线上订单退款
     */
    private Integer source;
    /**
     * 标识：1-支出；2-收入
     */
    private Integer mark;
    /**
     * 备注
     */
    private String remark;

    /**
     * 操作内容
     */
    private String content;

    @TableField(fill = FieldFill.INSERT)
    @JSONField(name = "create_time")
    private Date createTime;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
