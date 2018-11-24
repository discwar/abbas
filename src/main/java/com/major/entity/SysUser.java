package com.major.entity;

import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Getter;
import lombok.Setter;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;

import java.util.Date;

/**
 * <p>Title: Module Information         </p>
 * <p>Description: Function Description </p>
 * <p>Copyright: Copyright (c) 2018  </p>
 * <p>Company: AG Co., Ltd.             </p>
 * <p>Create Time: 2018/7/18 19:32      </p>
 *
 * @author LianGuoQing
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
@Getter
@Setter
@TableName("sys_user")
public class SysUser extends SuperEntity<SysUser> {

    /** 用户名称 */
    private String username;
    /** 密码 */
    private String password;
    /** 盐加密 */
    private String salt;
    /** 最后登陆IP */
    private String loginIp;
    /** 最后登陆时间 */
    private Date loginDate;

    /** 帐号状态：0-正常；1-禁用；2-删除 */
    @TableLogic
    private Integer status;
    private String remark;

    /**
     * 密码盐
     */
    public String getCredentialsSalt() {
        return this.getUsername() + this.getSalt();
    }

    /**
     * 生成随机盐
     */
    public void randomSalt() {
        // 一个Byte占两个字节，此处生成的3字节，字符串长度为6
        SecureRandomNumberGenerator secureRandom = new SecureRandomNumberGenerator();
        String hex = secureRandom.nextBytes(3).toHex();
        setSalt(hex);
    }

    @Override
    public String toString() {
        return "sys_user:" + super.getId();
    }

}
