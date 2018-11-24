package com.major.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>Title: Module Information         </p>
 * <p>Description: Function Description </p>
 * <p>Copyright: Copyright (c) 2018  </p>
 * <p>Company: AG Co., Ltd.             </p>
 * <p>Create Time: 2018/7/12 20:13      </p>
 *
 * @author LianGuoQing
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
@Getter
@Setter
@ToString
@TableName("user")
public class User extends SuperEntity<User> implements Serializable{

    /**
     * 用户名
     */
    private String username;
    /**
     * 用户昵称，系统自动分配
     */
    private String nickname;
    /**
     * 密码
     */
    private String password;
    /**
     * 手机号码
     */
    private String phone;
    /**
     * 头像路径
     */
    private String avatar;
    /**
     * 用户性别：0-女；1-男；-1无性别
     */
    private Integer sex;
    /**
     * 第三方标识：0-系统本身；1-微信；
     */
    private Integer userFrom;
    /**
     * 用户积分
     */
    private Integer score;
    /**
     * 用户累计积分
     */
    private Integer totalScore;
    /**
     * 钱包储值金额
     */
    private BigDecimal wallet;
    /**
     * 支付密码
     */
    private String payPwd;
    /**
     * 注册时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 最后登陆IP
     */
    private String loginIp;
    /**
     * 最后登陆时间
     */
    private Date loginTime;
    /**
     * 帐号状态：0-正常；1-禁用；2-删除
     */
    private Integer status;

    /**
     * 个推客户端身份ID
     */
    private String clientId;

}
