package com.pp.utils.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pp.mapper.SysLoginLogMapper;
import com.pp.utils.model.SysLoginLog;
import com.pp.utils.service.SysLoginLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;

@Service
public class SysLoginLogServiceImpl extends ServiceImpl<SysLoginLogMapper, SysLoginLog> implements SysLoginLogService {
    
    @Resource
    SysLoginLogMapper sysLoginLogMapper;
    
    @Override
    public Integer getTodayLoginCount() {
        return (int)sysLoginLogMapper.getTodayLoginCount(LocalDate.now().toString());
    }
}
