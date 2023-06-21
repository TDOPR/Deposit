package com.pp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pp.model.EvmEvent;

public interface EvmTROMEventService extends IService<EvmEvent> {
    boolean getEventExistByTxHash(String txHash);
}
