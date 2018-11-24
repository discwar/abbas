package com.major.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * <p>Title: Module Information         </p>
 * <p>Description: Function Description </p>
 * <p>Copyright: Copyright (c) 2018  </p>
 * <p>Company: AG Co., Ltd.             </p>
 * <p>Create Time: 2018/7/12 20:13      </p>
 *
 * @author LianGuoQing
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
@Getter
@Setter
@ToString
@TableName("user_address")
public class UserAddress extends SuperEntity<UserAddress> {

    private Long id;

    @JSONField(serialize = false)
    private Long userId;

    private String name;

    @ApiModelProperty("性别：0-女；1-男；-1无性别")
    private Integer sex;

    private String phone;


    /**
     * 地址描述（小区、写字楼）
     */
    @JSONField(name = "addr_desc")
    @ApiModelProperty("地址描述（小区、写字楼）")
    private String addrDesc;

    /**
     * 门牌号
     */
    @JSONField(name = "house_desc")
    @ApiModelProperty("地址描述（小区、写字楼）")
    private String houseDesc;

    /**
     * 经度
     */
    private Double longitude;

    /**
     * 维度
     */
    private Double latitude;

    @JSONField(name = "default_addr")
    @ApiModelProperty("是否默认地址  0 ：否  1：是")
    private Integer defaultAddr;

    /**
     * 详细地址
     */
    @ApiModelProperty("详细地址")
    private String address;

    /**
     * 有效标志
     */
    @JSONField(serialize = false)
    private Integer status;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
