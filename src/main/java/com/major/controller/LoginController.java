package com.major.controller;

import com.major.common.enums.StatusResultEnum;
import com.major.model.request.LoginRequest;
import com.major.model.response.BaseResponse;
import com.major.model.response.ResultResponse;
import com.major.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>Title: 登录认证 </p>
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
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public BaseResponse ajaxLogin(@RequestBody LoginRequest loginRequest) {
        Map<String, Object> resultMap = loginService.ajaxLogin(loginRequest.getUsername(),
                loginRequest.getPassword(),
                loginRequest.getRememberMe());
        return ResultResponse.success(StatusResultEnum.SUCCESS, resultMap);
    }

    /**
     * 未登录，shiro应重定向到登录界面，此处返回未登录状态信息由前端控制跳转页面
     * @return
     */
    @GetMapping("/unlogin")
    public BaseResponse unlogin() {
        return ResultResponse.error(StatusResultEnum.NOT_LOGIN_IN);
    }

    @GetMapping("/unauth")
    public BaseResponse unauth() {
        return ResultResponse.error(StatusResultEnum.UN_AUTHORIZED);
    }


}
