package com.pp.service;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pp.model.EvmUserWallet;

public interface EvmUserWalletService extends IService<EvmUserWallet> {
    
    EvmUserWallet getByAddress(String address, SFunction<EvmUserWallet, ?>... columns);
    
    
}
