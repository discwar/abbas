package com.major.shiro;

import com.major.common.constant.Constants;
import com.major.common.constant.RedisConstants;
import com.major.common.util.DateUtils;
import com.major.common.util.MessageUtils;
import com.major.common.util.ShiroUtils;
import com.major.common.util.SystemLogUtils;
import com.major.entity.SysUser;
import com.major.service.ISysUserService;
import com.major.service.RedisService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * <p>Title: Module Information         </p>
 * <p>Description: Function Description </p>
 * <p>Copyright: Copyright (c) 2018  </p>
 * <p>Company: AG Co., Ltd.             </p>
 * <p>Create Time: 2018/7/30 9:52      </p>
 *
 * @author LianGuoQing
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {

    /**
     * 过期时间默认为一天
     */
    private static final Long EXPIRE_TIME = 60*60*24L;

    /**
     * 锁定10分钟
     */
    private static final Long LOCK_EXPIRE_TIME = 60*10L;

    private static final String LOCK = "LOCK";

    @Value(value = "${user.password.maxRetryCount}")
    private Integer maxRetryCount;

    @Autowired
    private RedisService redisService;

    @Autowired
    private ISysUserService sysUserService;

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        SimplePrincipalCollection coll = (SimplePrincipalCollection) info.getPrincipals();
        SysUser user = (SysUser) coll.getPrimaryPrincipal();
        String username = user.getUsername();

        String loginCountKey = RedisConstants.AG_LOGIN_COUNT_PREFIX + username;
        Integer retryCount = (Integer) redisService.get(loginCountKey);

        if (retryCount == null) {
            retryCount = 0;
            redisService.set(loginCountKey, 0L, EXPIRE_TIME);
        }

        String isLockKey = RedisConstants.AG_LOCK_PREFIX + username;
        if (StringUtils.equals(LOCK, String.valueOf(redisService.get(isLockKey)))) {
            String msg = MessageUtils.message("user.password.retry.limit.exceed", retryCount);
            SystemLogUtils.log(username, Constants.LOGIN_FAIL, msg);
            throw new LockedAccountException(msg);
        }

        boolean match = super.doCredentialsMatch(token, info);

        if (!match) {
            // 设置过期时间为当天
            retryCount = redisService.increment(loginCountKey, 1L, DateUtils.getRemainSecondsOneDay(DateUtils.getNowDate())).intValue();
            UsernamePasswordToken upToken = (UsernamePasswordToken) token;
            String password = String.valueOf(upToken.getPassword());

            String msg;
            if (retryCount >= maxRetryCount) {
                redisService.set(isLockKey, LOCK, LOCK_EXPIRE_TIME);
                redisService.set(loginCountKey, retryCount, LOCK_EXPIRE_TIME);
                msg = MessageUtils.message("user.password.retry.limit.exceed", retryCount);
            } else {
                msg = MessageUtils.message("user.password.retry.limit.count", retryCount);
            }

            SystemLogUtils.log(username, Constants.LOGIN_FAIL, msg);
            throw new IncorrectCredentialsException(msg);
        } else {
            redisService.remove(loginCountKey, isLockKey);

            SystemLogUtils.log(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success"));
            recordLoginInfo(user);
        }

        return match;
    }

    /**
     * 记录登录信息
     */
    public void recordLoginInfo(SysUser user) {
        user.setLoginIp(ShiroUtils.getIp());
        user.setLoginDate(DateUtils.getNowDate());
        sysUserService.updateUser(user);
    }

}
