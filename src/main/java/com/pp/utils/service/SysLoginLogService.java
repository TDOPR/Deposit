package com.pp.utils.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pp.utils.model.SysLoginLog;

public interface SysLoginLogService extends IService<SysLoginLog> {
    Integer getTodayLoginCount();
}
