package com.pp.scheduled;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.pp.enums.*;
import com.pp.model.EvmRecharge;
import com.pp.model.EvmWithdraw;
import com.pp.service.EvmRechargeService;
import com.pp.service.EvmWithdrawService;
import com.pp.service.WalletsService;
import com.pp.utils.annotation.RedisLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class BlockTaskScheduledJob {
    @Autowired
    private EvmWithdrawService evmWithdrawService;
    
    @Autowired
    private EvmRechargeService evmRechargeService;
    
    @Autowired
    private WalletsService walletsService;
    
//    /**
//     * 区块链 每隔5秒拉取充值记录表任务状态
//     */
//    @Scheduled(fixedDelay = 5000)
//    @RedisLock
//    public void scanRechargeData() {
//        //获取充值任务状态是打币成功的
//        UpdateWrapper<EvmRecharge> updateWrapper;
//        List<EvmRecharge> list = evmRechargeService.list(new LambdaQueryWrapper<EvmRecharge>()
//                .select(EvmRecharge::getActualAmount, EvmRecharge::getAddress, EvmRecharge::getId)
//                .eq(EvmRecharge::getStatus), RechargeStatusEnum.RECHARGE_SUCCESS.getStatus())
//
//        //log.info("scanRechargeData list={}",list);
//        if (CollectionUtil.isEmpty(list)){
//            return;
//        }
//
//        //根据区块链地址修改用户钱包金额
//        List<Long> idList = new ArrayList<>();
//        for (EvmRecharge evmRecharge : list) {
//            walletsService.updateWallet(evmRecharge.getAddress(), evmRecharge.getActualAmount(), FlowingActionEnum.INCOME, FlowingTypeEnum.RECHARGE);
//            idList.add(evmRecharge.getId());
//        }
//
//        //修改任务状态
//        updateWrapper = Wrappers.update();
//        updateWrapper.lambda()
//                .set(EvmRecharge::getStatus, RechargeStatusEnum.TO_RECORDED_SUCCESS.getStatus())
//                .in(EvmRecharge::getId, idList);
//        evmRechargeService.update(updateWrapper);
//    }
    
    
//    /**
//     * 每隔30秒拉取提现表任务状态
//     */
//    @Scheduled(fixedDelay = 30000)
//    @RedisLock
//    public void scanWithdrawData() {
//        //获取提现任务状态是打币成功的
//        UpdateWrapper<EvmWithdraw> updateWrapper;
//        List<EvmWithdraw> list = evmWithdrawService.list(
//                new LambdaQueryWrapper<EvmWithdraw>()
//                        .select(EvmWithdraw::getId, EvmWithdraw::getUserId, EvmWithdraw::getAmount, EvmWithdraw::getStatus)
//                        .eq(EvmWithdraw::getCoinId, CoinUnitEnum.USDT.getId())
//                        .in(EvmWithdraw::getStatus, Arrays.asList(WithdrawStatusEnum.SUCCESS.getStatus(), WithdrawStatusEnum.BLOCK_COIN_PRINTING_FAILED.getStatus()))
//        );
//        //log.info("scanWithdrawData list={}",list);
//        if (CollectionUtil.isEmpty(list)){
//            return;
//        }
//
//        List<Long> idList = new ArrayList<>();
//        for (EvmWithdraw evmWithdraw : list) {
//            if (evmWithdraw.getStatus().equals(WithdrawStatusEnum.SUCCESS.getStatus())) {
//                walletsService.reduceFrozenAmount(evmWithdraw.getUserId(), evmWithdraw.getAmount());
//            } else {
//                //如果区块链打币失败 则把冻结的金额返还给客户
//                walletsService.unFrozenAmount(evmWithdraw.getUserId(), evmWithdraw.getAmount());
//            }
//            idList.add(evmWithdraw.getId());
//        }
//
//        //修改任务状态为已结算
//        updateWrapper = Wrappers.update();
//        updateWrapper.lambda()
//                .set(EvmWithdraw::getStatus, WithdrawStatusEnum.TO_AMOUNT_SUCCESS.getStatus())
//                .in(EvmWithdraw::getId, idList);
//        evmWithdrawService.update(updateWrapper);
//    }
}
