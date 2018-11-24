package com.major.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-10-11
 */
@TableName("log_sms")
public class LogSms extends Model<LogSms> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 发送者ID
     */
    @TableField("user_id")
    private Integer userId;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 验证码
     */
    private String code;
    /**
     * 验证返回码
     */
    @TableField("return_code")
    private String returnCode;
    /**
     * 验证类型： 0-注册登录；1-绑定手机号；2-用户校验； 3-商户订单短信通知
     */
    @TableField("sms_type")
    private Integer smsType;
    /**
     * 验证时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 商户ID商户短信通知
     */
    @TableField("shop_id")
    private Integer shopId;
    /**
     * 发送回执ID,可根据该ID查询具体的发送状态
     */
    @TableField("biz_id")
    private String bizId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public Integer getSmsType() {
        return smsType;
    }

    public void setSmsType(Integer smsType) {
        this.smsType = smsType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "LogSms{" +
        ", id=" + id +
        ", userId=" + userId +
        ", phone=" + phone +
        ", code=" + code +
        ", returnCode=" + returnCode +
        ", smsType=" + smsType +
        ", createTime=" + createTime +
        ", shopId=" + shopId +
        ", bizId=" + bizId +
        "}";
    }
}
