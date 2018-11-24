package com.major.model.response;

import com.major.model.StatusModel;
import lombok.Data;

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
@Data
public class BaseResponse<T> {

    /**
     * 响应状态
     */
    private StatusModel status;
    /**
     * 响应结果
     */
    private T data;

    public BaseResponse(StatusModel status, T data) {
        this.status = status;
        this.data = data;
    }

}
