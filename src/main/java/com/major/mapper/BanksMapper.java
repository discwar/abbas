package com.major.mapper;

import com.major.entity.Banks;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 银行表 Mapper 接口
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-09-20
 */
public interface BanksMapper extends BaseMapper<Banks> {


    /**
     * 获取银行数据分页列表
     * @param page
     * @param bankName
     * @return
     */
    @Select({
            "<script> "+
                    "      SELECT id,bank_name,bank_key,bank_sort,logo_url,status,create_time   FROM banks WHERE  " +
                    "         `status`=1  " +
                    "        <if test='bankName!=null '>  " +
                    "            AND bank_name =#{bankName}  " +
                    "        </if>  " +
                    "        ORDER BY  " +
                    "        create_time DESC "+
                    "</script>"}  )
    List<Map<String, Object>> selectBanksPage(Pagination page,
                                               @Param("bankName") String bankName);

    /**
     * 获取银行数据列表
     * @return
     */
    @Select({
            "<script> "+
                    "      SELECT id,bank_name,bank_key,bank_sort,logo_url,status,create_time  FROM banks WHERE  " +
                    "         `status`=1  " +
                    "        ORDER BY  " +
                    "        create_time DESC "+
                    "</script>"}  )
    List<Map<String, Object>> selectBanksList();


}
