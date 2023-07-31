package com.pp.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pp.model.AppUsers;
import com.pp.model.DonaTransferUsdt;
import com.pp.model.EvmRecharge;
import com.pp.model.EvmUserWallet;
import com.pp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;

@Component
public class TradeManager {
    
    @Autowired
    private EvmWithdrawService evmWithdrawService;
    
    @Autowired
    private EvmRechargeService evmRechargeService;
    
    @Autowired
    private WalletsService walletsService;
    
    @Autowired
    private AppUserService appUserService;
    
    @Autowired
    private EvmUserWalletService evmUserWalletService;
    
    @Autowired
    private  DonaTransferUsdtService donaTransferUsdtService;
    
    @Transactional
    public void evmRecharge(String userAddress, BigDecimal rechargeAmount, Integer level, Integer chainId, Integer rechargeStatus) {
        
        AppUsers userId = appUserService.getOne(new LambdaQueryWrapper<AppUsers>().select(AppUsers::getId).eq(AppUsers::getUserAddress, userAddress));
        
        EvmRecharge evmRecharge = EvmRecharge.builder()
                .uid(userId.getId())
                .address(userAddress)
                .actualAmount(rechargeAmount)
                .userLevel(level)
                .chainId(chainId)
                .status(rechargeStatus)
                .build();
        evmRechargeService.save(evmRecharge);
        
    }
    
    @Transactional
    public void donaTransferUsdt(String userAddress, BigDecimal donaTransferUsdtAmount,  Integer chainId) {
        
        AppUsers email = appUserService.getOne(new LambdaQueryWrapper<AppUsers>().select(AppUsers::getId).eq(AppUsers::getUserAddress, userAddress));
        
        DonaTransferUsdt donaTransferUsdt = DonaTransferUsdt.builder()
                .email(email.getEmail())
                .address(userAddress)
                .donaTransferUsdtAmount(donaTransferUsdtAmount)
                .chainId(chainId)
                .build();
        donaTransferUsdtService.save(donaTransferUsdt);
        
    }
    
    @Transactional
    public void evmUserWallet(String userAddress, BigDecimal rechargeAmount, Integer level, Integer chainId) {
        AppUsers appUsers = appUserService.getOne(new LambdaQueryWrapper<AppUsers>().select(AppUsers::getId).eq(AppUsers::getUserAddress, userAddress));
        
        EvmUserWallet evmUserWallet = EvmUserWallet.builder()
                .userId(appUsers.getId())
                .rechargeAmount(rechargeAmount)
                .userAddress(userAddress)
                .userLevel(level)
                .chainId(chainId)
                .build();
        evmUserWalletService.save(evmUserWallet);
    }
}
