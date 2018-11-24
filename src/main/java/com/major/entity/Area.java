package com.major.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

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
@TableName("area")
public class Area extends Model<Area> {

    @TableId(value="id", type= IdType.AUTO)
    private Long id;

    private Long parentId;

    private String name;

    /**
     * 简称
     */
    private String shortName;

    /**
     * 排序号
     */
    private Long areaSort;

    /**
     * 地区首字母
     */
    private String areaKey;

    /**
     * 地区级别：1-省/直辖市；2-市；3-区/县
     */
    private Integer areaType;

    /**
     * 状态：0-禁用；1-启用
     */
    private Integer status;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
