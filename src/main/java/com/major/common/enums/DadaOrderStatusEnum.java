package com.major.common.enums;

import lombok.Getter;

/**
 * @author xuquanming
 * @date 2018/9/11 15:51
 */
@Getter
public enum DadaOrderStatusEnum {
    /**
     *
     * 订单状态(待接单＝1 待取货＝2 配送中＝3 已完成＝4 已取消＝5 已过期＝7 指派单=8 妥投异常之物品返回中=9
     * 妥投异常之物品返回完成=10 创建达达运单失败=1000 可参考文末的状态说明）
     *
     */
    WAIT_RECEIPT(1,"待接单"),
    WAIT_PICK_UP(2,"待取货"),
    DISPATCHING(3,"配送中"),
    DONE(4,"订单完成"),
    CLEAN(5,"已取消"),
    EXPIRED(7,"已过期"),
    ASSIGN(8,"指派单"),
    SEND_ABNORMALITY_ITEM_RETURN(9,"妥投异常之物品返回中"),
    SEND_ABNORMALITY_ITEM_RETURN_DONE(10,"妥投异常之物品返回完成"),
    CREATE_ORDER_FAIL(1000,"创建达达运单失败");

    private Integer value;
    private  String des;

    DadaOrderStatusEnum(Integer value, String des) {
        this.value = value;
        this.des = des;
    }

    public static DadaOrderStatusEnum getEnum(Integer type){
        for (DadaOrderStatusEnum dadaOrderStatusEnum:  DadaOrderStatusEnum.values()){
            if (type.equals(dadaOrderStatusEnum.value)){
                return dadaOrderStatusEnum;
            }
        }
        return null;
    }


}
