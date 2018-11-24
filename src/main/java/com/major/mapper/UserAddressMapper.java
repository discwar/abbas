package com.major.mapper;


import com.major.entity.UserAddress;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>Title: Module Information         </p>
 * <p>Description: Function Description </p>
 * <p>Copyright: Copyright (c) 2018  </p>
 * <p>Company: AG Co., Ltd.             </p>
 * <p>Create Time: 2018/7/13 10:24      </p>
 *
 * @author LianGuoQing
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
public interface UserAddressMapper extends BaseMapper<UserAddress> {
    /**
     * 完整地址拼接
     * @param id
     * @return
     */
    @Select("SELECT " +
            " CONCAT( " +
            " ( SELECT CONCAT( address, house_desc ) FROM user_address WHERE id = #{id} )  " +
            " ) AS address_detail")
    String addressDetail(@Param("id") Long id);

}
