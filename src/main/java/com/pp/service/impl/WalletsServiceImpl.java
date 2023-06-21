package com.pp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pp.enums.AlgebraEnum;
import com.pp.enums.ChainTypeEnum;
import com.pp.enums.FlowingActionEnum;
import com.pp.enums.FlowingTypeEnum;
import com.pp.mapper.AppUserMapper;
import com.pp.mapper.EvmUserWalletMapper;
import com.pp.mapper.WalletsMapper;
import com.pp.model.*;
import com.pp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@Service
public class WalletsServiceImpl extends ServiceImpl<WalletsMapper, Wallets> implements WalletsService {
    
    @Autowired
    EvmUserWalletService evmUserWalletService;
    
    @Autowired
    WalletLogsService walletLogsService;
    
    @Autowired
    TreePathService treePathService;
    
    @Autowired
    EvmRechargeService evmRechargeService;
    
    @Resource
    EvmUserWalletMapper evmUserWalletMapper;
    
    @Resource
    AppUserMapper appUserMapper;
    
    
    
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateWallet(String blockAddress, BigDecimal amount, FlowingActionEnum flowingActionEnum, FlowingTypeEnum flowingTypeEnum, Integer chainId) {

        EvmUserWallet evmUserWallet = evmUserWalletService.getOne(new LambdaQueryWrapper<EvmUserWallet>().select(EvmUserWallet::getUserId).eq(EvmUserWallet::getUserAddress, blockAddress).eq(EvmUserWallet::getChainId, chainId));

        boolean flag = this.lookUpdateWallets(evmUserWallet.getUserId(), amount, flowingActionEnum, chainId);
        if (flag) {
            //插入流水记录
            walletLogsService.insertWalletLogs(evmUserWallet.getUserId(), amount, flowingActionEnum, flowingTypeEnum, chainId);
        }
        return flag;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean lookUpdateWallets(Integer userId, BigDecimal amount, FlowingActionEnum flowingActionEnum, Integer chainId) {
        int ret;
        int alegra;
        if (flowingActionEnum.equals(FlowingActionEnum.INCOME)) {
            ret = this.baseMapper.lockUpdateAddWallet(userId, amount);
            List<TreePath> refIds = treePathService.list(new LambdaQueryWrapper<TreePath>().select(TreePath::getAncestor).eq(TreePath::getDescendant, userId).ge(TreePath::getLevel, 1).lt(TreePath::getLevel,2));

            for(TreePath refId : refIds){
                if(evmUserWalletMapper.getUserLevel(userId).compareTo(evmUserWalletMapper.getUserLevel(refId.getAncestor()))>-1){
                    continue;
                }else{
                    
                    switch (refId.getLevel()){
                        case 1:
                            alegra = this.baseMapper.lockUpdateAddWallet(refId.getAncestor(), amount.multiply(AlgebraEnum.FIRST.getRewardProportion()));
                            if(alegra == 1){
                                walletLogsService.insertWalletAlegraLogs(refId.getAncestor(), userId, appUserMapper.findUserAddressByUserId(refId.getAncestor()), evmUserWalletMapper.getUserLevel(userId), amount.multiply(AlgebraEnum.FIRST.getRewardProportion()), flowingActionEnum, FlowingTypeEnum.ALGEBRA, chainId);
                            }
                            break;
                        case 2:
                            alegra = this.baseMapper.lockUpdateAddWallet(refId.getAncestor(), amount.multiply(AlgebraEnum.SECOND.getRewardProportion()));
                            if(alegra == 1){
                                walletLogsService.insertWalletAlegraLogs(refId.getAncestor(), userId, appUserMapper.findUserAddressByUserId(refId.getAncestor()), evmUserWalletMapper.getUserLevel(userId), amount.multiply(AlgebraEnum.SECOND.getRewardProportion()), flowingActionEnum, FlowingTypeEnum.ALGEBRA, chainId);
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
            
        } else {
            //减
            ret = this.baseMapper.lockUpdateReduceWallet(userId, amount);
        }
        return ret == 1;
    }
}
