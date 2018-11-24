package com.major.model.response;

import com.major.entity.Goods;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xuquanming
 * @date 2018/9/17 17:56
 */
@Data
public class GoodsResponse extends Goods {

    @ApiModelProperty("分类类型：4：采摘套票 5：果园直销 6：组团采摘")
    private Integer categoryType;

    @ApiModelProperty("针对进出口-所属洲Id'")
    private String continentId;


}
