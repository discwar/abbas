package com.major.mapper;

import com.major.entity.SysConfig;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface SysConfigMapper extends BaseMapper<SysConfig> {

    /**
     * 根据类型获取配置
     * @param osType
     * @return
     */
    @Select("SELECT * FROM sys_config WHERE os_type = #{osType} LIMIT 0,1 ")
    SysConfig selectSysConfigByOsType(@Param("osType") Integer osType);


}
