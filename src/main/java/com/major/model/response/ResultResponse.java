package com.major.model.response;

import com.major.common.enums.StatusResultEnum;
import com.major.model.StatusModel;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>Title: 通用结果响应 </p>
 * <p>Description: Function Description </p>
 * <p>Copyright: Copyright (c) 2018  </p>
 * <p>Company: AG Co., Ltd.             </p>
 * <p>Create Time: 2018/7/10 17:32      </p>
 *
 * @author LianGuoQing
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
@Getter
@Setter
public class ResultResponse<T> extends BaseResponse<T> {

    public ResultResponse(StatusResultEnum statusResult, Object... args) {
        super(new StatusModel(statusResult.getCode(), statusResult.getCodeMsg(args), statusResult.getStatusMsg(args)), (T) statusResult.name());
    }

    public ResultResponse(StatusResultEnum statusResult) {
        this(statusResult, (T) statusResult.name());
    }

    public ResultResponse(StatusResultEnum statusResult, T data) {
        super(new StatusModel(statusResult.getCode(), statusResult.getCodeMsg(), statusResult.getStatusMsg()), data);
    }

    /**
     * 业务响应成功
     * @param statusResult 响应状态体
     * @return
     */
    public static ResultResponse success(StatusResultEnum statusResult) {
        return new ResultResponse(statusResult);
    }

    /**
     * 业务响应成功
     * @param statusResult 响应状态体
     * @param data 响应消息数据
     * @return
     */
    public static ResultResponse success(StatusResultEnum statusResult, Object data) {
        return new ResultResponse(statusResult, data);
    }

    /**
     * 业务响应失败
     * @param statusResult 响应状态体
     * @param args 消息参数，该参数将用于格式化异常代码中的消息字符串
     * @return
     */
    public static ResultResponse error(StatusResultEnum statusResult, Object... args) {
        return new ResultResponse(statusResult, args);
    }

}
