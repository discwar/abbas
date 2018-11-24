package com.major.model.request;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户组表
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-08-15
 */
@Getter
@Setter

public class UserGroupRequest {

    @ApiModelProperty(value = "用户组名称", required = true)
    @NotNull(message = "groupName不能为空")
    private String groupName;


    @ApiModelProperty(value = "数据发送使用key-value形式;样例:1.八月一号注册的用户:<<1,2018-08-01>>;2.从八月十号到八月十一号消费超过100用户:<<1,2018-08-10>,<2,2018-08-11>,<6,100>>;注解：一对数据中,开头数字为运营表的Id", required = true)
    @NotNull(message = "groupKeyValue不能为空")
    private   List<Map<Integer,String>> groupKeyValue;

    @ApiModelProperty(value = "筛选状态:0-xx刚注册的用户;1-xx消费超过x元的用户;2-xx消费未超过x元的用户;3-24内未活跃的用户;4-全部用户", required = true)
    @NotNull(message = "searchType不能为空")
    private Integer searchType;


}
