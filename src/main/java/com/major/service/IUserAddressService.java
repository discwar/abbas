package com.major.service;


import com.major.entity.UserAddress;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>Title: 用户收货地址表数据服务层接口 </p>
 * <p>Description: Function Description </p>
 * <p>Copyright: Copyright (c) 2018  </p>
 * <p>Company: AG Co., Ltd.             </p>
 * <p>Create Time: 2018/7/13 10:35      </p>
 *
 * @author LianGuoQing
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
public interface IUserAddressService extends IService<UserAddress> {

    /**
     * 用户详细的
     * @param id
     * @return
     */
    String addressDetail(Long id);

}
