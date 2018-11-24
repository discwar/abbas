package com.major.service.impl;

import com.major.common.constant.Constants;
import com.major.common.util.DateUtils;
import com.major.entity.UserCoupon;
import com.major.mapper.UserCouponMapper;
import com.major.service.IUserCouponService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户优惠券表 服务实现类
 * </p>
 *
 * @author LianGuoQing
 * @since 2018-08-01
 */
@Service
public class UserCouponServiceImpl extends ServiceImpl<UserCouponMapper, UserCoupon> implements IUserCouponService {

    @Override
    public List<UserCoupon> findUserCouponList(Integer status) {
        Wrapper<UserCoupon> wrapper = new EntityWrapper<>();
        wrapper.where("uc.status={0}", status)
                .and("c.status={0}", Constants.STATUS_NORMAL)
                .andNew("(uc.deadline IS NOT NULL AND DATE_FORMAT(uc.deadline, '%Y-%m-%d') < CURDATE())")
                .or("(uc.deadline IS NULL AND DATE_FORMAT(c.deadline, '%Y-%m-%d') < CURDATE())");
        return baseMapper.selectUserCouponList(wrapper);
    }

    @Override
    public boolean addUserCoupon(UserCoupon userCoupon) {
        return super.insert(userCoupon);
    }

    @Override
    public boolean addUserCouponList(List<UserCoupon> userCouponList) {
        return super.insertBatch(userCouponList);
    }

    @Override
    public  List<UserCoupon> selectUserCouponListByUserId (Long userId, Long couponId) {
        return baseMapper.selectUserCouponListByUserId(userId,couponId);
    }

    @Override
    public Page<Map<String, Object>> selectUserCouponNotUserByUserId(Page<Map<String, Object>> page,  Long userId) {
        return page.setRecords(baseMapper.selectUserCouponNotUserByUserId(page,userId));
    }

    @Override
    public Page<Map<String, Object>> selectUserCouponInfoByUserId(Page<Map<String, Object>> page, Long userId, String discountDesc,  Integer status,
                                                                  String createTimeStart, String createTimeStop) {
        return page.setRecords(baseMapper.selectUserCouponInfoByUserId(page,userId,discountDesc,status,createTimeStart,createTimeStop));
    }

    @Override
    public boolean updateUserCoupon(Long userCouponId, Integer status) {
        UserCoupon userCoupon = new UserCoupon();
        userCoupon.setId(userCouponId);
        userCoupon.setStatus(status);

        if (Constants.COUPON_STATUS_USED.equals(status)) {
            userCoupon.setUseTime(DateUtils.getNowDate());
        }

        return super.updateById(userCoupon);
    }

}
