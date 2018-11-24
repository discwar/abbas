package com.major.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableLogic;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>Title: Module Information         </p>
 * <p>Description: Function Description </p>
 * <p>Copyright: Copyright (c) 2018  </p>
 * <p>Company: AG Co., Ltd.             </p>
 * <p>Create Time: 2018/7/15 18:04      </p>
 *
 * @author LianGuoQing
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
@Getter
@Setter
@ToString
public class ShoppingCart extends SuperEntity<ShoppingCart> {

    private Long userId;

    private Long shopId;

    private Long goodsId;

    private Long goodsNum;

    /**
     * 商品来源类型：0-特惠；1-折扣；2-普通（普通商品都参与满减）;3-1元购；4-秒杀；5-团购
     */
    @TableField(exist = false)
    private Integer sourceType;

    @TableLogic
    private Integer status;

}
