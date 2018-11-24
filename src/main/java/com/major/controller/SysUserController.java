package com.major.controller;

import com.major.common.aspectj.annotation.Log;
import com.major.common.constant.VersionConstants;
import com.major.common.enums.StatusResultEnum;
import com.major.common.util.ShiroUtils;
import com.major.entity.SysUser;
import com.major.model.request.RoleRequest;
import com.major.model.request.SysUserRequest;
import com.major.model.response.BaseResponse;
import com.major.model.response.ResultResponse;
import com.major.service.IMenuService;
import com.major.service.IRoleService;
import com.major.service.ISysUserService;
import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * web端系统用户管理
 * Created by Administrator on 2018/7/31.
 */
@Api(description="系统用户管理")
@RestController
@RequestMapping(value = VersionConstants.V1 + "/sysusers")
public class SysUserController extends BaseController {

    @Autowired
    private IRoleService roleService;

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private IMenuService menuService;

    @ApiOperation(value="新增角色")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token值", required = true),
            @ApiImplicitParam(paramType="body", name="roleRequest", value="信息参数", dataType="RoleRequest", required=true)
    })
    @Log(title = "角色管理", action = "新增角色")
    @RequiresPermissions("sysusers:roles:add")
    @PostMapping("/roles")
    public BaseResponse saveTips(
            @Valid @RequestBody RoleRequest roleRequest,
            BindingResult bindingResult) {
        super.validRequestBody(bindingResult);
        SysUser sysUser = ShiroUtils.getSysUser();
        boolean result = roleService.addRole(roleRequest,sysUser.getUsername());
        if (!result) {
            return ResultResponse.error(StatusResultEnum.DB_SAVE_ERROR, "新增角色");
        }
        return ResultResponse.success(StatusResultEnum.SUCCESS);
    }

    @ApiOperation(value="修改角色")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token值", required = true),
            @ApiImplicitParam(paramType="body", name="roleRequest", value="信息参数", dataType="RoleRequest", required=true)
    })
    @Log(title = "角色管理", action = "修改角色")
    @RequiresPermissions("sysusers:roles:update")
    @PutMapping("/roles/{role_id}")
    public BaseResponse putTips(@PathVariable("role_id") Long roleId,
                                @RequestBody RoleRequest roleRequest) {
        SysUser sysUser = ShiroUtils.getSysUser();
        boolean result = roleService.updateRole(roleRequest,sysUser.getUsername(),roleId);
        if (!result) {
            return ResultResponse.error(StatusResultEnum.DB_SAVE_ERROR, "修改角色");
        }
        return ResultResponse.success(StatusResultEnum.SUCCESS);
    }

    @ApiOperation(value = "删除角色")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token值", required = true)
    })
    @Log(title = "角色管理", action = "删除角色")
    @RequiresPermissions("sysusers:roles:delete")
    @DeleteMapping(value = "/roles/{role_id}")
    public BaseResponse deleteUserAddress(@PathVariable("role_id") Long roleId) {
        SysUser sysUser = ShiroUtils.getSysUser();
        boolean flag =  roleService.deleteRole(sysUser.getUsername(),roleId);
        if (!flag) {
            return ResultResponse.error(StatusResultEnum.DB_SAVE_ERROR, "删除角色");
        }
        return ResultResponse.success(StatusResultEnum.SUCCESS);
    }


    @ApiOperation(value="获取角色分页列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token值", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "int", name = "current_page", value = "当前第几页", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "int", name = "page_size", value = "每页几条", required = true)
               })
    @RequiresPermissions("sysusers:roles:list")
    @GetMapping("/roles/list")
    public BaseResponse getTipsList(
                                    @RequestParam("current_page") int currentPage,
                                    @RequestParam("page_size") int pageSize) {
        Page<Map<String, Object>> page = new Page<>(currentPage, pageSize);
        Page<Map<String, Object>> rolePageList = roleService.selectTipsPage(page);
        return ResultResponse.success(StatusResultEnum.SUCCESS, rolePageList);
    }

    @ApiOperation(value="获取所有角色列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token值", required = true)
           })
   @RequiresPermissions("sysusers:roles")
    @GetMapping("/roles")
    public BaseResponse getTipsList( ) {
        Map<String, Object>  rolePageList = roleService.selectRoleList();
        return ResultResponse.success(StatusResultEnum.SUCCESS, rolePageList);
    }


    @ApiOperation(value="新增管理员")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token值", required = true),
            @ApiImplicitParam(paramType="body", name="sysUserRequest", value="信息参数", dataType="SysUserRequest", required=true)
    })
    @Log(title = "管理员管理", action = "新增管理员")
    @RequiresPermissions("sysusers:users:add")
    @PostMapping("")
    public BaseResponse saveSysUser(
            @Valid   @RequestBody SysUserRequest sysUserRequest,
            BindingResult bindingResult) {
        super.validRequestBody(bindingResult);
        SysUser sysUser = ShiroUtils.getSysUser();
        boolean result = sysUserService.addSysUser(sysUserRequest,sysUser.getUsername());
        if (!result) {
            return ResultResponse.error(StatusResultEnum.DB_SAVE_ERROR, "新增管理员");
        }
        return ResultResponse.success(StatusResultEnum.SUCCESS);
    }

    @ApiOperation(value="修改管理员")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token值", required = true),
            @ApiImplicitParam(paramType="body", name="sysUserRequest", value="信息参数", dataType="SysUserRequest", required=true)
    })
    @Log(title = "管理员管理", action = "修改管理员")
    @RequiresPermissions("sysusers:users:update")
    @PutMapping("/{user_id}")
    public BaseResponse updateSysUser(@PathVariable("user_id") Long userId,
                                      @RequestBody SysUserRequest sysUserRequest) {
        SysUser sysUser = ShiroUtils.getSysUser();
        boolean result = sysUserService.updateSysUser(sysUserRequest,sysUser.getUsername(),userId);
        if (!result) {
            return ResultResponse.error(StatusResultEnum.DB_SAVE_ERROR, "修改管理员");
        }
        return ResultResponse.success(StatusResultEnum.SUCCESS);
    }

    @ApiOperation(value = "删除管理员")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token值", required = true)
    })
    @Log(title = "管理员管理", action = "删除管理员")
    @RequiresPermissions("sysusers:users:delete")
    @DeleteMapping(value = "/{user_id}")
    public BaseResponse deleteSysUser(@PathVariable("user_id") Long userId) {
        boolean flag =  sysUserService.deleteSysUser(userId);
        if (!flag) {
            return ResultResponse.error(StatusResultEnum.DB_SAVE_ERROR, "删除管理员");
        }
        return ResultResponse.success(StatusResultEnum.SUCCESS);
    }

    @ApiOperation(value="获取管理员分页列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token值", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "int", name = "current_page", value = "当前第几页", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "int", name = "page_size", value = "每页几条", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "user_name", value = "用户名", required = false),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "remark", value = "备注名", required = false),
            @ApiImplicitParam(paramType = "query", dataType = "int", name = "role_id", value = "角色id", required = false),
    })
    @RequiresPermissions("sysusers:users:list")
    @GetMapping("/list")
    public BaseResponse getSysUserPage(
            @RequestParam("current_page") int currentPage,
            @RequestParam("page_size") int pageSize,
            @RequestParam(value = "user_name", required = false) String userName,
            @RequestParam(value = "remark", required = false) String remark,
            @RequestParam(value = "role_id", required = false) Long roleId) {
        Page<Map<String, Object>> page = new Page<>(currentPage, pageSize);
        Page<Map<String, Object>> rolePageList = sysUserService.selectSysUserPage(page,userName,remark,roleId);
        return ResultResponse.success(StatusResultEnum.SUCCESS, rolePageList);
    }

    @ApiOperation(value="个人管理")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token值", required = true),
            @ApiImplicitParam(paramType="body", name="sysUserRequest", value="信息参数", dataType="SysUserRequest", required=true)
    })
    @Log(title = "个人管理", action = "个人管理")
    @RequiresPermissions("sysusers:users:changepwd")
    @PutMapping("/{user_id}/actions/changepwd")
    public BaseResponse changeSysUserPwd(@PathVariable("user_id") Long userId,
                                         @RequestBody SysUserRequest sysUserRequest) {
        boolean result = sysUserService.changeSysUserPwd(sysUserRequest,userId);
        if (!result) {
            return ResultResponse.error(StatusResultEnum.DB_SAVE_ERROR, "个人管理");
        }
        return ResultResponse.success(StatusResultEnum.SUCCESS);
    }

    @ApiOperation(value="获取当前角色下菜单信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token值", required = true)
    })
    @RequiresPermissions("sysusers:roles:byRoleId")
    @GetMapping("/roles/{role_id}")
    public BaseResponse selectRoleListByRoleId(@PathVariable("role_id") Long roleId ) {
        Map<String, Object>  rolePageList = roleService.selectRoleListByRoleId(roleId);
        return ResultResponse.success(StatusResultEnum.SUCCESS, rolePageList);
    }

    @ApiOperation(value="获取当前管理员信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token值", required = true)
    })
    @RequiresPermissions("sysusers:userId")
    @GetMapping("/{sys_user_id}")
    public BaseResponse selectUserById(@PathVariable("sys_user_id") Long userId ) {
       SysUser sysUser = sysUserService.selectUserById(userId);
        return ResultResponse.success(StatusResultEnum.SUCCESS, sysUser);
    }


    @ApiOperation(value = "校验用户名是否同名",notes = "可用于实时校验用户名是否同名")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token值", required = true)
    })
    @RequiresPermissions("sysUsers:checkSysUser")
    @GetMapping(value = "/check_sys_user/{name}")
    public BaseResponse checkSysUserName(@PathVariable("name") String name) {
        boolean flag =  sysUserService.checkSysUserName(name);
        if (!flag) {
            return ResultResponse.error(StatusResultEnum.DB_SAVE_ERROR, "校验失败");
        }
        return ResultResponse.success(StatusResultEnum.SUCCESS);
    }


    @ApiOperation(value="重置密码")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token值", required = true)
    })
    @Log(title = "管理员管理", action = "重置用户密码")
    @RequiresPermissions("sysusers:users:resetpwd")
    @PutMapping("/{sys_user_id}/actions/reset_pwd")
    public BaseResponse resetSysUserPwd(@PathVariable("sys_user_id") Long userId ) {
        boolean result = sysUserService.resetSysUserPwd(userId);
        if (!result) {
            return ResultResponse.error(StatusResultEnum.DB_SAVE_ERROR, "重置用户密码");
        }
        return ResultResponse.success(StatusResultEnum.SUCCESS);
    }

}
