package com.major.service.impl;

import com.major.common.constant.Constants;
import com.major.common.enums.StatusResultEnum;
import com.major.common.exception.AgException;
import com.major.common.util.Md5Utils;
import com.major.common.util.ShiroUtils;
import com.major.config.UserConfig;
import com.major.entity.SysUser;
import com.major.entity.SysUserRole;
import com.major.mapper.SysUserMapper;
import com.major.model.request.SysUserRequest;
import com.major.service.ISysUserRoleService;
import com.major.service.ISysUserService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Autowired
    private ISysUserRoleService sysUserRoleService;

    @Autowired
    private UserConfig userConfigl;

    @Override
    public SysUser selectUserByUserName(String userName) {
        return baseMapper.selectUserByUserName(userName);
    }

    @Override
    public int updateUser(SysUser user) {
        return baseMapper.updateUser(user);
    }

    @Override
    public boolean addSysUser(SysUserRequest sysUserRequest, String username) {
        if(StringUtils.isEmpty(sysUserRequest.getPassword())) {
            throw new AgException(StatusResultEnum.REQUIRE_ARGUMENT,"密码");
        }
        SysUser sysUserOld=baseMapper.selectUserByUserName(sysUserRequest.getUsername());
         if(sysUserOld!= null) {
             throw new AgException(StatusResultEnum.DB_SAVE_ERROR,"存在用户名同名");
        }
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(sysUserRequest, sysUser);
        sysUser.randomSalt();
        String password = Md5Utils.encryptPassword(sysUserRequest.getPassword(), sysUser.getCredentialsSalt(),2);
        sysUser.setPassword(password);
        sysUser.setStatus(Constants.STATUS_ENABLE);
        super.insert(sysUser);
        //添加表
        SysUserRole sysUserRole=new SysUserRole();
        sysUserRole.setRoleId(sysUserRequest.getRoleId().intValue());
        sysUserRole.setSysUserId(sysUser.getId().intValue());
        return  sysUserRoleService.addSysUserRole(sysUserRole);

    }

    @Override
    public boolean updateSysUser(SysUserRequest sysUserRequest, String username, Long userId){
        SysUser sysUser = new SysUser();
        sysUser.setId(userId);
        sysUser.setRemark(sysUserRequest.getRemark());

        if(sysUserRequest.getRoleId()!=null){
            //先删，再加
            sysUserRoleService.deleteSysUserRole(userId);
            SysUserRole sysUserRole=new SysUserRole();
            sysUserRole.setRoleId(sysUserRequest.getRoleId().intValue());
            sysUserRole.setSysUserId(sysUser.getId().intValue());
            sysUserRoleService.addSysUserRole(sysUserRole);
            //清除权限缓存
            ShiroUtils.clearCachedAuthorizationInfo();
        }
        return  super.updateById(sysUser);
    }

    @Override
    public  boolean deleteSysUser(  Long userId){
        SysUser sysUser = new SysUser();
        sysUser.setId(userId);
        //清除权限缓存
        sysUserRoleService.deleteSysUserRole(userId);
        ShiroUtils.clearCachedAuthorizationInfo();
        return  sysUser.deleteById();
    }
    @Override
    public  Page<Map<String, Object>> selectSysUserPage(Page<Map<String, Object>> page,String userName,String remark,Long roleId){
        Wrapper ew = new EntityWrapper();
        ew.where("su.`status`={0}", Constants.STATUS_ENABLE);
        //自动判空，当有值时sql含义表示 =
        Map<String,Object> map=new HashMap<>();
        map.put("r.id",roleId);
        ew.allEq(map);
        if(StringUtils.isNotEmpty(userName)){
            ew.like("su.username",userName);
        }
        if(StringUtils.isNotEmpty(remark)){
            ew.like("su.remark",remark);
        }
        ew.groupBy("su.id");
        ew.orderBy(" su.create_time DESC ");
        return page.setRecords(baseMapper.selectSysUserPage(page,ew));
    }

    @Override
    public boolean changeSysUserPwd(SysUserRequest sysUserRequest, Long userId){
        if(StringUtils.isEmpty(sysUserRequest.getNewpassword()) ||
                StringUtils.isEmpty(sysUserRequest.getPassword())) {
            throw new AgException(StatusResultEnum.REQUIRE_ARGUMENT,"新密码或原密码");
        }

        SysUser sysUserOld = super.selectById(userId);
        SysUser sysUser = new SysUser();
        //把用户输的原密码按上一次的加密方式重新加密一次，再对比
            String passwordold = Md5Utils.encryptPassword(sysUserRequest.getPassword(),sysUserOld.getCredentialsSalt(),2);
            if(!StringUtils.equals(passwordold,sysUserOld.getPassword())) {
                throw new AgException(StatusResultEnum.DB_SAVE_ERROR,"原密码输入错误");
            }
            sysUser.setId(userId);
            sysUser.randomSalt();
            sysUser.setUsername(sysUserOld.getUsername());
            //校验通过之后，加密新密码
            String passwordnew = Md5Utils.encryptPassword(sysUserRequest.getNewpassword(), sysUser.getCredentialsSalt(),2);
            sysUser.setPassword(passwordnew);
            sysUser.setRemark(sysUserRequest.getRemark());
        return  super.updateById(sysUser);
    }
    @Override
    public SysUser selectUserById(Long userId) {
        SysUser sysUser=   super.selectById(userId);
        return  sysUser;
    }

    @Override
    public boolean checkSysUserName(String name){
        SysUser sysUserOld= this.selectUserByUserName(name);
        if(sysUserOld!= null) {
            throw new AgException(StatusResultEnum.DB_SAVE_ERROR,"存在用户名同名");
        }
        return true;
    }

    @Override
    public boolean resetSysUserPwd( Long Id){
        String passWord= Md5Utils.stringToMD5(userConfigl.getResetPassWord());
        SysUser sysUser = super.selectById(Id);
        sysUser.randomSalt();
        sysUser.setUsername(sysUser.getUsername());
        //重置密码不需要任何校验，直接加密
        String passWordNew = Md5Utils.encryptPassword(passWord, sysUser.getCredentialsSalt(),2);
        sysUser.setPassword(passWordNew);
        return  super.updateById(sysUser);
    }

    @Override
    public Map<String, Object> selectRoleBySysUserId(Long sysUserId) {
        return baseMapper.selectRoleBySysUserId(sysUserId);
    }
}
