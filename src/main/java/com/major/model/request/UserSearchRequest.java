package com.major.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @Author: zhangzhenliang
 * @Date: 2018/8/9 11:22
 * @Version 1.0
 */
@Getter
@Setter
public class UserSearchRequest {


    @ApiModelProperty(value = "用户名称", required = false)
    private String nickname;

    @ApiModelProperty(value = "手机号", required = false)
    private String phone;

    @ApiModelProperty(value = "绑定名", required = false)
    private Integer source;

    @ApiModelProperty(value = "注册时间开始", required = false)
    private Date createTimeStart;

    @ApiModelProperty(value = "注册时间结束", required = false)
    private Date createTimeStop;



    @ApiModelProperty(value = "活跃时间开始", required = false)
    private String loginTimeStart;

    @ApiModelProperty(value = "活跃时间结束", required = false)
    private String loginTimeStop;


    @ApiModelProperty(value = "消费时间开始", required = false)
    private String billTimeStart;
    @ApiModelProperty(value = "消费时间结束", required = false)
    private String billTimeStop;

    @ApiModelProperty(value = "消费金额开始", required = false)
    private String totalMoneyStart;
    @ApiModelProperty(value = "消费金额结束", required = false)
    private String totalMoneyStop;


    @ApiModelProperty(value = "积分数开始", required = false)
    private String totalScoreStart;
    @ApiModelProperty(value = "积分数结束", required = false)
    private String totalScoreStop;

    @ApiModelProperty(value = "是否为创客 0-否，1-是", required = false)
    private Integer isMaker;

    @ApiModelProperty(value = "创客等级", required = false)
    private String ranksName;

    @ApiModelProperty(value = "钱包金额开始", required = false)
    private String walletStart;
    @ApiModelProperty(value = "钱包金额结束", required = false)
    private String walletStop;

    @ApiModelProperty(value = "邀请人数开始", required = false)
    private String invitationNumStart;
    @ApiModelProperty(value = "邀请人数结束", required = false)
    private String invitationNumStop;

    @ApiModelProperty(value = "消费次数开始", required = false)
    private String billNumStart;
    @ApiModelProperty(value = "消费次数结束", required = false)
    private String billNumStop;

}
