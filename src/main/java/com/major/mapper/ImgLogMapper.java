package com.major.mapper;

import com.major.entity.ImgLog;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 图片记录表 Mapper 接口
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-09-13
 */
public interface ImgLogMapper extends BaseMapper<ImgLog> {


    /**
     * 根据表名和表中id查询
     * @param fromTable
     * @param fromId
     * @return
     */
    @Select("SELECT *  FROM img_log WHERE from_table=#{fromTable} AND from_id=#{fromId} AND `status`=1 and from_field=#{fromField} ")
    List<ImgLog> selectImgLogsByTable(@Param("fromTable") String fromTable,@Param("fromId") Long fromId,@Param("fromField") String fromField);


    @Select({
            "<script> "+
                    " SELECT l.id,l.oper_id,l.oper_type,l.from_table,l.from_id ,su.username,l.from_field,l.img_url,l.img_size,l.status   " +
                    " FROM img_log l  " +
                    "left join sys_user su ON su.id=l.oper_id  " +
                    " WHERE  " +
                    "         l.`status`=1  " +
                    "        <if test='fromTable!=null '>  " +
                    "            AND l.from_table =#{fromTable}  " +
                    "        </if>  " +
                    "        <if test='fromField!=null '>  " +
                    "            AND l.from_field =#{fromField}  " +
                    "        </if>  " +
                    "        <if test='operType!=null '>  " +
                    "            AND l.oper_type =#{operType}  " +
                    "        </if>  " +
                    "        ORDER BY  " +
                    "       l.create_time DESC "+
                    "</script>"}  )
    List<Map<String, Object>> selectImgLogPage(Pagination page,
                                                 @Param("fromTable") String fromTable, @Param("fromField") String fromField,
                                                 @Param("operType") Integer operType);


  }
