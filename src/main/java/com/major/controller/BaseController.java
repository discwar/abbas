package com.major.controller;

import com.major.common.enums.StatusResultEnum;
import com.major.common.exception.AgException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

/**
 * <p>Title: Module Information         </p>
 * <p>Description: Function Description </p>
 * <p>Copyright: Copyright (c) 2018  </p>
 * <p>Company: AG Co., Ltd.             </p>
 * <p>Create Time: 2018/8/8 14:54      </p>
 *
 * @author LianGuoQing
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
public class BaseController {

    /**
     * 请求参数验证
     * @param result
     */
    protected void validRequestBody(BindingResult result) {
        if (result.hasErrors()) {
            // 存储错误信息的字符串变量
            StringBuffer msgBuffer = new StringBuffer();

            // 错误字段集合
            List<FieldError> fieldErrors = result.getFieldErrors();

            for (FieldError fieldError : fieldErrors) {
                // 获取错误信息
                msgBuffer.append(fieldError.getDefaultMessage() + ",");
            }

            // 抛出错误信息
            throw new AgException(StatusResultEnum.REQUIRE_ARGUMENT_VALID_FAIL, msgBuffer.toString());
        }
    }

}
