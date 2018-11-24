package com.major.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * <p>Title: Module Information         </p>
 * <p>Description: Function Description </p>
 * <p>Copyright: Copyright (c) 2018  </p>
 * <p>Company: AG Co., Ltd.             </p>
 * <p>Create Time: 2018/7/13 9:44      </p>
 *
 * @author LianGuoQing
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
@Getter
@Setter
@ToString
public class AreaRequest  {

    @ApiModelProperty(value = "父级省份/城市id", required = true)
    @NotNull(message = "父级省份/城市不能为空")
    private Long parentId;

    @ApiModelProperty(value = "城市名称", required = true)
    @NotNull(message = "城市名称不能为空")
    private String name;

    @ApiModelProperty(value = "简称", required = false)
    private String shortName;

    @ApiModelProperty(value = "排序号", required = false)
    private Long areaSort;

    @ApiModelProperty(value = "地区级别：1-省/直辖市；2-市；3-区/县", required = true)
    @NotNull(message = "地区级别不能为空")
    private Integer areaType;



}
