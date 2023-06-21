package com.pp.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pp.enums.FlowingActionEnum;
import com.pp.mapper.EvmWithdrawMapper;
import com.pp.model.EvmWithdraw;
import com.pp.service.EvmWithdrawService;
import com.pp.service.WalletsService;
import com.pp.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class EvmWithdrawServiceImpl extends ServiceImpl<EvmWithdrawMapper, EvmWithdraw> implements EvmWithdrawService {
    @Autowired
    private WalletsService walletsService;
    
//    @Override
//    public JsonResult pageList(PageParam<EvmWithdraw, AppUserWithdrawCondition> pageParam) {
//        if (pageParam.getSearchParam() == null) {
//            pageParam.setSearchParam(new AppUserWithdrawCondition());
//        }
//        IPage<EvmWithdrawVO> iPage = this.baseMapper.page(pageParam.getPage(), pageParam.getSearchParam());
//        for (EvmWithdrawVO evmWithdrawVO : iPage.getRecords()) {
//            evmWithdrawVO.setAuditStatusName(WithdrawStatusEnum.getDescByStatus(evmWithdrawVO.getAuditStatus()));
//        }
//        return JsonResult.successResult(new PageVO<>(iPage.getTotal(), iPage.getPages(), iPage.getRecords()));
//    }
//
//    @Override
//    public JsonResult check(AuditCheckDTO auditCheckDTO) {
//        UpdateWrapper<EvmWithdraw> wrapper = Wrappers.update();
//        wrapper.lambda()
//                .set(EvmWithdraw::getAuditStatus, auditCheckDTO.getState())
//                .set(EvmWithdraw::getAuditTime, LocalDateTime.now())
//                .eq(EvmWithdraw::getId, auditCheckDTO.getId());
//        EvmWithdraw evmWithdraw = this.getById(auditCheckDTO.getId());
//        if (auditCheckDTO.getState().equals(WithdrawStatusEnum.CHECK_SUCCESS.getStatus())) {
//            //审核通过 触发提现逻辑
//            if (evmWithdraw.getCoinId().equals(CoinUnitEnum.USDT.getId())) {
//                //区块链提现需要等链上打币成功再
//                wrapper.lambda().set(EvmWithdraw::getStatus, WithdrawStatusEnum.CHECK_SUCCESS.getStatus());
//            } else {
//                boolean flag = this.playFiat(evmWithdraw);
//                if (flag) {
//                    //扣除冻结的金额
//                    walletsService.reduceFrozenAmount(evmWithdraw.getUserId(), evmWithdraw.getAmount());
//                } else {
//                    //如果调用提现接口异常则返回冻结金额给用户
//                    walletsService.unFrozenAmount(evmWithdraw.getUserId(), evmWithdraw.getAmount());
//                }
//            }
//        } else {
//            //取消冻结的金额
//            walletsService.unFrozenAmount(evmWithdraw.getUserId(), evmWithdraw.getAmount());
//        }
//        this.update(wrapper);
//        return JsonResult.successResult();
//    }
//
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public JsonResult usdtWithdrawal(UsdtWithdrawalDTO usdtWithdrawalDTO) {
//        if (GlobalProperties.isProdEnv()) {
//            String cacheKey = CacheKeyPrefixConstants.CAPTCHA_CODE + usdtWithdrawalDTO.getUuid();
//            String code = RedisUtil.getCacheObject(cacheKey);
//            if (code == null) {
//                return JsonResult.failureResult(ReturnMessageEnum.VERIFICATION_CODE_EXPIRE);
//            }
//            if (!code.equals(usdtWithdrawalDTO.getCode())) {
//                return JsonResult.failureResult(ReturnMessageEnum.VERIFICATION_CODE_ERROR);
//            }
//        }
//
//        CoinNetworkSourceEnum coinNetworkSourceEnum = CoinNetworkSourceEnum.nameOf(usdtWithdrawalDTO.getNetwordName());
//
//        if (coinNetworkSourceEnum == null) {
//            return JsonResult.failureResult(ReturnMessageEnum.UB_SUPPORT_NETWORD);
//        }
//
//        Integer userId = JwtTokenUtil.getUserIdFromToken(ThreadLocalManager.getToken());
//        Wallets wallets = walletsService.selectColumnsByUserId(userId, Wallets::getWalletAmount);
//
//        //提现金额不能大于钱包余额
//        if (usdtWithdrawalDTO.getAmount().compareTo(wallets.getWalletAmount()) > 0) {
//            return JsonResult.failureResult(ReturnMessageEnum.AMOUNT_EXCEEDS_BALANCE);
//        }
//
//        //计算手续费
//        BigDecimal free = coinNetworkSourceEnum.getFree().multiply(usdtWithdrawalDTO.getAmount());
//        if (free.compareTo(coinNetworkSourceEnum.getMinFreeAmount()) < 0) {
//            //如果手续费低于提现要求的最低,则使用最低限制的
//            free = coinNetworkSourceEnum.getMinFreeAmount();
//        }
//
//        //生成提现记录
//        EvmWithdraw evmWithdraw = EvmWithdraw.builder()
//                .userId(userId)
//                .address(usdtWithdrawalDTO.getAddress())
//                .fee(free)
//                .actualAmount(usdtWithdrawalDTO.getAmount().subtract(free))
//                .amount(usdtWithdrawalDTO.getAmount())
//                .coinName(CoinUnitEnum.USDT.getName())
//                .coinUnit(usdtWithdrawalDTO.getNetwordName())
//                .coinId(CoinUnitEnum.USDT.getId())
//                .build();
//
//        //需要冻结提现金额
//        walletsService.frozenAmount(userId, usdtWithdrawalDTO.getAmount());
//        if (usdtWithdrawalDTO.getAmount().subtract(new BigDecimal(EasyTradeSettingEnum.CHECK_MIN_AMOUNT.intValue())).doubleValue() > 0) {
//            //大额提现需要审核,设置提现任务状态为待审核
//            evmWithdraw.setStatus(WithdrawStatusEnum.UNDER_REVIEW.getStatus());
//            evmWithdraw.setAuditStatus(WithdrawStatusEnum.UNDER_REVIEW.getStatus());
//        } else {
//            //Usdt提现 需要通过定时任务去扫描区块链打币有没有成功
//            evmWithdraw.setStatus(WithdrawStatusEnum.CHECK_SUCCESS.getStatus());
//        }
//        this.save(evmWithdraw);
//        return JsonResult.successResult();
//    }
//
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public JsonResult fiatWithdrawal(WalletDTO walletDTO) {
//        if (GlobalProperties.isProdEnv()) {
//            String cacheKey = CacheKeyPrefixConstants.CAPTCHA_CODE + walletDTO.getUuid();
//            String code = RedisUtil.getCacheObject(cacheKey);
//            if (code == null) {
//                return JsonResult.failureResult(ReturnMessageEnum.VERIFICATION_CODE_EXPIRE);
//            }
//
//            if (!code.equals(walletDTO.getCode())) {
//                return JsonResult.failureResult(ReturnMessageEnum.VERIFICATION_CODE_ERROR);
//            }
//        }
//
//        Integer userId = JwtTokenUtil.getUserIdFromToken(ThreadLocalManager.getToken());
//        Wallets wallets = walletsService.selectColumnsByUserId(userId, Wallets::getWalletAmount);
//
//        if (walletDTO.getAmount().compareTo(wallets.getWalletAmount()) > 0) {
//            //提现金额不能大于钱包余额
//            return JsonResult.failureResult(ReturnMessageEnum.AMOUNT_EXCEEDS_BALANCE);
//        }
//
//        //计算费率
//        BigDecimal fee = walletDTO.getAmount().multiply(EasyTradeConfig.FIAT_FEE);
//        //实际提现美元
//        BigDecimal actualAmountUSD = walletDTO.getAmount().subtract(fee);
//
//        //生成提现记录
//        EvmWithdraw evmWithdraw = EvmWithdraw.builder()
//                .userId(userId)
//                .fee(fee)
//                .amount(walletDTO.getAmount())
//                .actualAmount(actualAmountUSD)
//                .coinName(CoinUnitEnum.FIAT.getName())
//                .coinId(CoinUnitEnum.FIAT.getId())
//                .build();
//
//        if (evmWithdraw.getAmount().subtract(new BigDecimal(EasyTradeSettingEnum.CHECK_MIN_AMOUNT.intValue())).doubleValue() > 0) {
//            //需要冻结提现金额
//            walletsService.frozenAmount(userId, evmWithdraw.getAmount());
//            evmWithdraw.setStatus(WithdrawStatusEnum.UNDER_REVIEW.getStatus());
//            evmWithdraw.setAuditStatus(WithdrawStatusEnum.UNDER_REVIEW.getStatus());
//        } else {
//            //小额直接调用支付接口
//            evmWithdraw.setStatus(WithdrawStatusEnum.TO_AMOUNT_SUCCESS.getStatus());
//            boolean flag = this.playFiat(evmWithdraw);
//            if (!flag) {
//                return JsonResult.failureResult();
//            }
//            //扣减钱包余额
//            walletsService.updateWallet(evmWithdraw.getAmount(), userId, FlowingActionEnum.EXPENDITURE, FlowingTypeEnum.WITHDRAWAL);
//        }
//        this.save(evmWithdraw);
//        return JsonResult.successResult();
//    }
    
    /**
     * 调用法币提现API
     *
     * @param evmWithdraw 充值信息
     * @return
     */
    private boolean playFiat(EvmWithdraw evmWithdraw) {
        //调用法币充值接口 TODO
        {
        
        }
        return true;
    }
}
