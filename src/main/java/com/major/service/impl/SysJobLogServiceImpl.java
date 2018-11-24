package com.major.service.impl;

import com.major.entity.SysJobLog;
import com.major.mapper.SysJobLogMapper;
import com.major.service.ISysJobLogService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Title: 定时任务调度日志表 服务实现类 </p>
 * <p>Description: Function Description </p>
 * <p>Copyright: Copyright (c) 2018  </p>
 * <p>Company: AG Co., Ltd.             </p>
 * <p>Create Time: 2018/7/31 14:32      </p>
 *
 * @author LianGuoQing
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
@Service
public class SysJobLogServiceImpl extends ServiceImpl<SysJobLogMapper, SysJobLog> implements ISysJobLogService {

    @Override
    public Page<Map<String, Object>> selectSysJobLogPage(Page<Map<String, Object>> page, String jobName, Integer isException,
                                                      String createTimeStart, String createTimeEnd) {
        Wrapper ew = new EntityWrapper();
        ew.where("is_exception <>3 ");
        Map<String,Object> map=new HashMap<>();
        map.put("job_name",jobName);
        map.put("is_exception",isException);
        ew.allEq(map);
        if(StringUtils.isNotEmpty(createTimeStart)) {
            ew.ge("create_time",createTimeStart);
        }
        if(StringUtils.isNotEmpty(createTimeEnd)) {
            ew.le("create_time",createTimeEnd);
        }
        ew.orderBy("create_time DESC ");
        return page.setRecords(baseMapper.selectSysJobLogPage(page,ew));
    }
}
