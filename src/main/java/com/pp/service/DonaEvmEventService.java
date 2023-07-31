package com.pp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pp.model.DonaEvmEvent;
import com.pp.model.EvmEvent;

public interface DonaEvmEventService extends IService<DonaEvmEvent> {
//    boolean getEventExistByTxHash(String txHash);
    boolean getEventExistByTxHash(String txHash);
}
