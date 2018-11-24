package com.major.common.exception;

import com.major.common.enums.StatusResultEnum;
import com.major.model.response.BaseResponse;
import com.major.model.response.ResultResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>Title: 统一异常处理器 </p>
 * <p>Description: 所有控制器未捕获的异常将会在该类进行处理，并返回统一定义的异常响应信息。 </p>
 * <p>Copyright: Copyright (c) 2018  </p>
 * <p>Company: AG Co., Ltd.             </p>
 * <p>Create Time: 2018/6/14 0014 下午 10:18      </p>
 *
 * @author LianGuoQing
 *         <p>Update Time:                      </p>
 *         <p>Updater:                          </p>
 *         <p>Update Comments:                  </p>
 */
@RestControllerAdvice
@Slf4j
public class AgExceptionHandler {

    /**
     * 异常处理逻辑。
     *
     * @param e 异常对象
     * @param request HTTP请求对象
     * @param response HTTP响应对象
     * @return 异常信息响应VO
     * @throws Throwable
     * @since 0.1
     */
    @ExceptionHandler(Throwable.class)
    public BaseResponse handleException(Throwable e, HttpServletRequest request, HttpServletResponse response)
            throws Throwable {
        AgException agException = null;
        if (e instanceof AgException) {
            agException = (AgException) e;
        } else if (e instanceof LockedAccountException
                || e instanceof UnknownAccountException
                || e instanceof IncorrectCredentialsException
                || e instanceof DisabledAccountException) {
            agException = new AgException(StatusResultEnum.IDENTITY_AUTH_FAIL, e, e.getMessage());
        } else if (e instanceof UnauthorizedException) {
            agException = new AgException(StatusResultEnum.UN_AUTHORIZED, e);
        } else if (e instanceof MissingServletRequestParameterException) {
            String parameter = ((MissingServletRequestParameterException) e).getParameterName();
            agException = new AgException(StatusResultEnum.REQUIRE_ARGUMENT, e, parameter);
        } else {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            agException = new AgException(StatusResultEnum.INTERNAL_SERVER_ERROR, e.getMessage());
            log.error("[ERROR] ", e);
        }

        return new ResultResponse(agException.getStatusResult(), agException.getArgs());
    }

}