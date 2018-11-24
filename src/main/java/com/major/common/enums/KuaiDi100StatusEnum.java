package com.major.common.enums;

import lombok.Getter;

/**
 * @Author: zhangzhenliang
 * @Date: 2018/9/25 15:06
 * @Version 1.0
 */
@Getter
public enum KuaiDi100StatusEnum {

    /**
     * 	快递单当前签收状态，包括0在途中、1已揽收、2疑难、3已签收、4退签、5同城派送中、6退回、7转单等7个状态，其中4-7需要另外开通才有效
     */
    THE_WAY(0,"在途中"),
    COURIER(1,"已揽收"),
    DIFFICULT(2,"疑难"),
    DELIVER(3,"已签收"),
    BACK_SIGN(4,"退签"),
    TC_SEND_OUT(5,"同城派送中"),
    RETURN(6,"退回"),
    SLIP(7,"转单");


    private Integer value;
    private  String des;
    KuaiDi100StatusEnum( Integer value,String des){
        this.value=value;
        this.des=des;
    }

    public static KuaiDi100StatusEnum getEnum(Integer type){
        for (KuaiDi100StatusEnum dadaOrderStatusEnum:  KuaiDi100StatusEnum.values()){
            if (type.equals(dadaOrderStatusEnum.value)){
                return dadaOrderStatusEnum;
            }
        }
        return null;
    }
}
