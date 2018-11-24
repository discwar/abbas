package com.major.shiro;

import com.major.common.util.ShiroUtils;
import com.major.entity.SysUser;
import com.major.service.IMenuService;
import com.major.service.IRoleService;
import com.major.service.LoginService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>Title: shiro身份校验核心类 </p>
 * <p>Description: Function Description </p>
 * <p>Copyright: Copyright (c) 2018  </p>
 * <p>Company: AG Co., Ltd.             </p>
 * <p>Create Time: 2018/7/26 17:08      </p>
 *
 * @author LianGuoQing
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    private IMenuService menuService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private LoginService loginService;

    /**
     * 身份认证：验证用户输入的账号和密码是否正确
     * 只有用户登录的时候才会执行身份认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
            throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        String username = token.getUsername();
        String password = String.valueOf(token.getPassword());

        SysUser sysUser = loginService.authentication(username, password);

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(
                sysUser,
                sysUser.getPassword(),
                ByteSource.Util.bytes(sysUser.getCredentialsSalt()),
                getName());

        return info;
    }

    /**
     * 授权
     * 每次URL请求时，都要经过这边授权，通过才放行。
     * 注意：第二次请求会走缓存，所以有任何权限变更都需要去清理缓存权限，否则权限变更不生效
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
        Long sysUserId = ShiroUtils.getSysUserId();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        // 角色加入AuthorizationInfo认证对象
        authorizationInfo.setRoles(roleService.selectRoleKeys(sysUserId));
        // 权限加入AuthorizationInfo认证对象
        authorizationInfo.setStringPermissions(menuService.selectPermsByUserId(sysUserId));
        return authorizationInfo;
    }

    /**
     * 清理缓存权限
     */
    public void clearCachedAuthorizationInfo() {
        this.clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
    }

}
