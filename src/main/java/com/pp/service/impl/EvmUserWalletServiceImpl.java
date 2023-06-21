package com.pp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pp.mapper.EvmUserWalletMapper;
import com.pp.model.EvmUserWallet;
import com.pp.service.EvmUserWalletService;
import org.springframework.stereotype.Service;

@Service
public class EvmUserWalletServiceImpl extends ServiceImpl<EvmUserWalletMapper, EvmUserWallet> implements EvmUserWalletService {
    @Override
    public EvmUserWallet getByAddress(String address, SFunction<EvmUserWallet, ?>... columns) {
        return this.getOne(new LambdaQueryWrapper<EvmUserWallet>()
                .select(columns)
                .eq(EvmUserWallet::getUserAddress, address)
        );
    }
}
