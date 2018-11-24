package com.major.mapper;

import com.major.entity.GroupPicking;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 组团采摘表 Mapper 接口
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-08-31
 */
public interface GroupPickingMapper extends BaseMapper<GroupPicking> {

    /**
     * 组团采购分页列表
     * sql注解:第一个嵌套统计所有参与人数，第二个得到最近出团时间
     * @param page
     * @param goodsName
     * @param shopName
     * @return
     */
    @Select({
            "<script> "+
                  "SELECT  " +
                    "gs.name,s.shop_name,gs.person_num,od.partake_num,gs.id as goods_id ,a.out_time,gs.status  " +
                    "FROM  goods gs  " +
                    "LEFT JOIN ( " +
                                "SELECT  " +
                                "  SUM(goods_num) as partake_num,goods_id,o.order_status_type  " +
                                 " FROM order_detail a  " +
                                " LEFT JOIN `order` o ON a.order_id=o.id " +
                                " WHERE " +
                                "  o.is_pay=1 AND o.order_status_type=2 " +
                                "GROUP BY goods_id " +
                                ")od ON gs.id=od.goods_id " +
                    "LEFT JOIN shop s ON s.id= gs.shop_id " +
                    "LEFT JOIN( " +
                                "SELECT od.goods_id,MAX(gp.out_time) as out_time  FROM order_detail od  " +
                                "LEFT JOIN `order` o ON od.order_id=o.id " +
                                "LEFT JOIN group_picking gp ON gp.id=o.group_picking_id " +
                                "WHERE o.is_pay=1 AND o.order_status_type=2  GROUP BY od.goods_id " +
                                ")a ON a.goods_id=od.goods_id " +
                    "LEFT JOIN goods_category gc ON gc.id=gs.category_id  " +
                    "WHERE " +
                    "gc.category_type = 6   " +
                    "       AND   gs.status <![CDATA[ <> ]]>  0   " +
                    "        <if test='goodsName!=null '>  " +
                    "            AND gs.`name`  like concat('%',#{goodsName},'%')  " +
                    "        </if>  " +
                    "        <if test='shopName!=null '>  " +
                    "            AND s.shop_name  like concat('%',#{shopName},'%')  " +
                    "        </if>  " +
                    "       GROUP BY gs.id ORDER BY a.out_time  DESC "+
                    "</script>"}  )
    List<Map<String,Object>> selectGroupPickingPage(Pagination page, @Param("goodsName") String goodsName, @Param("shopName") String shopName );


}
