package com.major.config;

import com.baomidou.mybatisplus.mapper.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * <p>Title: 自定义填充策略接口实现 </p>
 * <p>Description: 公共字段自动填充 </p>
 * <p>Copyright: Copyright (c) 2018  </p>
 * <p>Company: AG Co., Ltd.             </p>
 * <p>Create Time: 2018/7/13 14:57      </p>
 *
 * @author LianGuoQing
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
@Component
public class MyMetaObjectHandler extends MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        Object createTime = this.getFieldValByName("createTime", metaObject);
        if (createTime == null) {
            this.setFieldValByName("createTime", new Timestamp(System.currentTimeMillis()), metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Object createTime = this.getFieldValByName("updateTime", metaObject);
        if (createTime == null) {
            this.setFieldValByName("updateTime", new Timestamp(System.currentTimeMillis()), metaObject);
        }
    }

}
