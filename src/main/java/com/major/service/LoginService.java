package com.major.service;

import com.major.common.constant.Constants;
import com.major.common.constant.UserConstants;
import com.major.common.enums.SysUserStatusEnum;
import com.major.common.util.MessageUtils;
import com.major.common.util.ShiroUtils;
import com.major.common.util.SystemLogUtils;
import com.major.entity.Menu;
import com.major.entity.SysUser;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Title: Module Information         </p>
 * <p>Description: Function Description </p>
 * <p>Copyright: Copyright (c) 2018  </p>
 * <p>Company: AG Co., Ltd.             </p>
 * <p>Create Time: 2018/7/27 16:53      </p>
 *
 * @author LianGuoQing
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
@Service
public class LoginService {

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private IMenuService menuService;


    @Autowired
    private IRoleService roleService;



    public SysUser authentication(String username, String password) {
        // 用户名或密码为空 错误
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            String msg = MessageUtils.message("not.null");
            SystemLogUtils.log(username, Constants.LOGIN_FAIL, msg);
            throw new UnknownAccountException(msg);
        }

        // 用户名、密码如果不在指定范围内 错误
        if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
                || password.length() > UserConstants.PASSWORD_MAX_LENGTH
                || username.length() < UserConstants.USERNAME_MIN_LENGTH
                        || username.length() > UserConstants.USERNAME_MAX_LENGTH) {
            String msg = MessageUtils.message("user.password.not.match");
            SystemLogUtils.log(username, Constants.LOGIN_FAIL, msg);
            throw new IncorrectCredentialsException(msg);
        }

        SysUser user = sysUserService.selectUserByUserName(username);

        // 用户不存在
        if (user == null || SysUserStatusEnum.DELETED.getCode().equals(user.getStatus())) {
            String msg = MessageUtils.message("user.not.exists");
            SystemLogUtils.log(username, Constants.LOGIN_FAIL, msg);
            throw new UnknownAccountException(msg);
        }

        // 用户被禁用
        if (SysUserStatusEnum.DISABLE.getCode().equals(user.getStatus())) {
            String msg = MessageUtils.message("user.blocked", user.getRemark());
            SystemLogUtils.log(username, Constants.LOGIN_FAIL, msg);
            throw new DisabledAccountException(msg);
        }

        return user;
    }

    public Map<String, Object> ajaxLogin(String username, String password, Boolean rememberMe) {
        Map<String, Object> resultMap = new HashMap<>(1);

        UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe);
        Subject subject = ShiroUtils.getSubject();
        subject.login(token);

        // 取身份信息
        SysUser sysUser = ShiroUtils.getSysUser();
        //校验用户分配的角色是否有绑定店铺
        roleService.checkRoleShop(sysUser.getId());
        Map<String, Object> userMap = new HashMap<>(3);
        userMap.put("user_id",sysUser.getId());
        userMap.put("user_name",sysUser.getUsername());
        userMap.put("remark",sysUser.getRemark());

        // 根据用户id取出菜单
        List<Menu> menus = menuService.selectMenusByUserId(sysUser.getId());
        resultMap.put("token", ShiroUtils.getSessionId());
        resultMap.put("menu_list", menus);
        resultMap.put("user_info", userMap);
        return resultMap;
    }

}
