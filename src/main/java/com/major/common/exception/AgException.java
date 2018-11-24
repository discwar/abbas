package com.major.common.exception;

import com.major.common.enums.StatusResultEnum;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>Title: 异常类 </p>
 * <p>Description: Function Description </p>
 * <p>Copyright: Copyright (c) 2018  </p>
 * <p>Company: AG Co., Ltd.             </p>
 * <p>Create Time: 2018/7/12 11:56      </p>
 *
 * @author LianGuoQing
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
@Getter
@Slf4j
public class AgException extends RuntimeException {

    /**
     * 响应状态码枚举
     */
    private StatusResultEnum statusResult;

    private Object[] args;

    /**
     * 构造指定异常代码与消息参数的业务异常。
     *
     * @param statusResult 异常代码
     * @param args 消息参数，该参数将用于格式化异常代码中的消息字符串
     */
    public AgException(StatusResultEnum statusResult, Object... args) {
        this(statusResult, null, args);
    }

    /**
     * 构造指定异常代码、异常原因与消息参数的业务异常。
     *
     * @param statusResult 异常代码
     * @param cause 异常消息
     * @param args 消息参数，该参数将用于格式化异常代码中的消息字符串
     */
    public AgException(StatusResultEnum statusResult, Throwable cause, Object... args) {
        super(statusResult.getCodeMsg(args), cause);
        log.error("[ERROR] ", cause);
        this.args = args;
        this.statusResult = statusResult;
    }

}
