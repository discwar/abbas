package com.major.common.enums;


import com.major.common.exception.AgException;
import lombok.Getter;

/**
 * <p>Title: 消息类型枚举类 </p>
 * <p>Description: Function Description </p>
 * <p>Copyright: Copyright (c) 2018  </p>
 * <p>Company: AG Co., Ltd.             </p>
 * <p>Create Time: 2018/10/12 11:56      </p>
 *
 * @author LianGuoQing
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
@Getter
public enum MessageTypeEnum {

    /**
     * 消息类型
     */
    ORDER_NOTIFY(1, "订单通知",1L),
    SYSTEM_NOTIFY(2, "系统通知",2L),
    SHOP_COMMENT_REPLY(3, "商家评论反馈回复",3L),
    AI_OFFICIAL_REPLY(4, "爱果官方反馈回复",4L);


    private Integer value;
    private String desc;
    private Long id;
    MessageTypeEnum(Integer value, String desc,Long id) {
        this.value = value;
        this.desc = desc;
        this.id=id;
    }

    public static MessageTypeEnum getMessageTypeEnum(Integer value) {
        for (MessageTypeEnum messageTypeEnum : MessageTypeEnum.values()) {
            if (messageTypeEnum.getValue().equals(value)) {
                return messageTypeEnum;
            }
        }

        throw new AgException(StatusResultEnum.REQUIRE_ARGUMENT_VALID_FAIL, "message_type值不在取值范围内！");
    }

}
