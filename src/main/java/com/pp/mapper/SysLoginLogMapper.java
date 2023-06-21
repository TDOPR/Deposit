package com.pp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pp.utils.model.SysLoginLog;

public interface SysLoginLogMapper extends BaseMapper<SysLoginLog> {
    
    long getTodayLoginCount(String now);
}
