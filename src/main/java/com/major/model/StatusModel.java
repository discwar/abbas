package com.major.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>Title: 响应状态 </p>
 * <p>Description: Function Description </p>
 * <p>Copyright: Copyright (c) 2018  </p>
 * <p>Company: AG Co., Ltd.             </p>
 * <p>Create Time: 2018/7/10 17:35      </p>
 *
 * @author LianGuoQing
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
@Getter
@Setter
public class StatusModel {

    /**
     * 响应返回码
     */
    private String code;

    /**
     * 响应描述，面向开发者
     */
    @JSONField(name = "code_msg")
    private String codeMsg;

    /**
     * 响应描述，面向用户
     */
    @JSONField(name = "status_msg")
    private String statusMsg;

    public StatusModel(String code, String codeMsg, String statusMsg) {
        this.code = code;
        this.codeMsg = codeMsg;
        this.statusMsg = statusMsg;
    }

}
