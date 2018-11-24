package com.major.common.enums;

import com.major.common.exception.AgException;
import lombok.Getter;

/**
 * @author xuquanming
 * @date 2018/9/19 16:20
 */
@Getter
public enum ShopRoleEnum {

    /**
     * 类型
     */
    ROLE_SELF_SUPPORT(0, "总部自营","ag_manage"),
    ROLE_AIGUO_SHOP(1, "爱果小店管理","small_manage"),
    ROLE_NEAR_FRUIT(2, "水果商家管理","fruits_manage"),
    ROLE_FARM(3, "农场管理","farm_manage"),
    ROLE_PLUCKING_GARDEN(4, "采摘园采摘园管理","plucking_garden_manage"),
    ROLE_IMPORT_FRUIT(5, "进出口管理","import_fruit_manage"),
    ROLE_FRUIT_AGL(6,"水果商家爱果小店管理","fruits_small_manage");

    private Integer value;
    private String desc;
    private String roleKey;

    ShopRoleEnum(Integer value, String desc,String roleKey) {
        this.value = value;
        this.desc = desc;
        this.roleKey=roleKey;
    }

    public static ShopRoleEnum getShopRoleEnum(Integer value) {
        for (ShopRoleEnum shopRoleEnum : ShopRoleEnum.values()) {
            if (shopRoleEnum.getValue().equals(value)) {
                return shopRoleEnum;
            }
        }

        throw new AgException(StatusResultEnum.REQUIRE_ARGUMENT_VALID_FAIL, "shop_type值不在取值范围内！");
    }
}
