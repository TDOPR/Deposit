package com.pp.manager;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.pp.enums.FlowingActionEnum;
import com.pp.enums.FlowingTypeEnum;
import com.pp.mapper.AppUserMapper;
import com.pp.mapper.EvmUserWalletMapper;
import com.pp.model.CoinConfig;
import com.pp.model.EvmEvent;
import com.pp.enums.ChainTypeEnum;
import com.pp.enums.RechargeStatusEnum;
import com.pp.mapper.CoinConfigDao;
import com.pp.model.EvmUserWallet;
import com.pp.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.Log;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class EventManager {
    @Resource
    private CoinConfigDao coinConfigDao;
    
    @Resource
    private ERC20WalletHandleService erc20WalletHandleService;
    
    @Resource
    private ERC20BSCWalletHandleService erc20BSCWalletHandleService;
    @Resource
    private CoinConfigService coinConfigService;
    
    @Resource
    private EvmEventService evmEventService;
    
    @Autowired
    private WalletsService walletsService;
    
    @Resource
    private TradeManager tradeManager;
    
    @Autowired
    private  EvmUserWalletService evmUserWalletService;
    
    @Resource
    private AppUserMapper appUserMapper;
    
    public void analyzeETHEvent() throws Exception {
        CoinConfig scanDataConfig = coinConfigDao.getScanDataConfig();
        if (scanDataConfig == null) {
            return;
        }
        BigInteger currentBlock = erc20WalletHandleService.queryBlockLast();
        BigInteger oldBlock = scanDataConfig.getBlockNo();
        oldBlock = oldBlock.add(BigInteger.ONE);
        if (currentBlock.subtract(oldBlock).compareTo(new BigInteger("5000")) > -1) {
            currentBlock = oldBlock.add(new BigInteger("4999"));
        }
        log.info(log.getName() + ".scanDataJob currentBlock:{}, oldBlock:{}", currentBlock, oldBlock);
        executeETHOneBlock(oldBlock, currentBlock);
        coinConfigDao.updateActionSeqById(scanDataConfig.getId(), currentBlock);
    }
    
    public void analyzeBSCEvent() throws Exception {
        CoinConfig scanDataConfig = coinConfigDao.getBscScanDataConfig();
        if (scanDataConfig == null) {
            return;
        }
        BigInteger currentBlock = erc20BSCWalletHandleService.queryBlockLast();
        BigInteger oldBlock = scanDataConfig.getBlockNo();
        oldBlock = oldBlock.add(BigInteger.ONE);
        if (currentBlock.subtract(oldBlock).compareTo(new BigInteger("5000")) > -1) {
            currentBlock = oldBlock.add(new BigInteger("4999"));
        }
        log.info(log.getName() + ".scanDataJob currentBlock:{}, oldBlock:{}", currentBlock, oldBlock);
        executeBscOneBlock(oldBlock, currentBlock);
        coinConfigDao.updateActionSeqById(scanDataConfig.getId(), currentBlock);
    }
    
    private void executeETHOneBlock(BigInteger oldBlock, BigInteger toBlock) throws Exception {
    
        CoinConfig eventConfig = coinConfigService.getOne(
                new LambdaQueryWrapper<CoinConfig>()
                        .select(CoinConfig::getContract)
                        .eq(CoinConfig::getCoinType, "EVENT")
                        .eq(CoinConfig::getChainId, ChainTypeEnum.ETH.getChainId())
        );
        
        String eventContract = eventConfig.getContract();
        
        String buyNodeCreated = erc20WalletHandleService.createBuyNodeEvent();
        
        EthFilter ethFilter = erc20WalletHandleService.createFilter(oldBlock, toBlock, eventContract);
        ethFilter.addOptionalTopics(buyNodeCreated);
        List<Log> logsList = erc20WalletHandleService.getLogByFilter(ethFilter);
        if (CollectionUtil.isEmpty(logsList)) {
            return;
        }
        List<EvmEvent> evmEventEntityArrayList = new ArrayList<>();
        for (Log logsLog : logsList) {
            String txHash = logsLog.getTransactionHash();
            if (evmEventService.getEventExistByTxHash(txHash)) {
                continue;
            }
            ArrayList topics = (ArrayList) logsLog.getTopics();
            String eventName = topics.get(0).toString();
            
            if (eventName.equalsIgnoreCase(buyNodeCreated)) {
                String userAddress = "0x" + topics.get(1).toString().substring(2).replace("000000000000000000000000", "");
                BigDecimal rechargeAmount = new BigDecimal(new BigInteger(topics.get(2).toString().substring(2),16));
                Integer userLevel = new Integer(new BigInteger(topics.get(3).toString().substring(2),16).toString());
                tradeManager.evmRecharge(userAddress, rechargeAmount, userLevel, ChainTypeEnum.GOERLI.getChainId(), RechargeStatusEnum.RECHARGE_SUCCESS.getStatus());
                //                测试数据GOERLI
                UpdateWrapper<EvmUserWallet> updateWrapper = Wrappers.update();
                updateWrapper.lambda()
                        .set(EvmUserWallet::getUserAddress, userAddress)
                        .set(EvmUserWallet::getUserLevel, userLevel)
                        .eq(EvmUserWallet::getUserId, appUserMapper.findUserIdByUserAddress(userAddress)).eq(EvmUserWallet::getChainId, ChainTypeEnum.GOERLI.getChainId());
                evmUserWalletService.update(updateWrapper);
//                tradeManager.evmUserWallet(userAddress, rechargeAmount, userLevel, ChainTypeEnum.GOERLI.getChainId());
//                evmUserWalletMapper.setLevelByRecord(userAddress, userLevel, ChainTypeEnum.ETH.getChainId());
//                测试数据GOERLI
                walletsService.updateWallet(userAddress, rechargeAmount, FlowingActionEnum.INCOME, FlowingTypeEnum.RECHARGE, ChainTypeEnum.GOERLI.getChainId());
                
                String blockHash = logsLog.getBlockHash();
                EthBlock.Block ethBlock = erc20WalletHandleService.getBlockByHash(blockHash);
                Date date = new Date(ethBlock.getTimestamp().intValue() * 1000L);
                
                EvmEvent ethScanDataEntity = EvmEvent.builder()
                        .txHash(txHash)
                        .blockNum(new BigInteger(logsLog.getBlockNumber().toString(10)))
                        .createTime(date)
                        .chainId(ChainTypeEnum.ETH.getChainId())
                        .build();
                evmEventEntityArrayList.add(ethScanDataEntity);
            }
        }
        if (CollectionUtil.isNotEmpty(evmEventEntityArrayList)) {
            evmEventService.saveBatch(evmEventEntityArrayList);
        }
    }
    
    private void executeBscOneBlock(BigInteger oldBlock, BigInteger toBlock) throws Exception {
    
        CoinConfig eventConfig = coinConfigService.getOne(
                new LambdaQueryWrapper<CoinConfig>()
                        .select(CoinConfig::getContract)
                        .eq(CoinConfig::getCoinType, "EVENT")
                        .eq(CoinConfig::getChainId, ChainTypeEnum.BSC.getChainId())
        );
    
        String eventContract = eventConfig.getContract();
    
        String buyNodeCreated = erc20BSCWalletHandleService.createBuyNodeEvent();
    
        EthFilter ethFilter = erc20BSCWalletHandleService.createFilter(oldBlock, toBlock, eventContract);
        ethFilter.addOptionalTopics(buyNodeCreated);
        List<Log> logsList = erc20BSCWalletHandleService.getLogByFilter(ethFilter);
        if (CollectionUtil.isEmpty(logsList)) {
            return;
        }
        List<EvmEvent> evmEventEntityArrayList = new ArrayList<>();
        for (Log logsLog : logsList) {
            String txHash = logsLog.getTransactionHash();
            if (evmEventService.getEventExistByTxHash(txHash)) {
                continue;
            }
            ArrayList topics = (ArrayList) logsLog.getTopics();
            String eventName = topics.get(0).toString();
            String data = logsLog.getData();
        
            if (eventName.equalsIgnoreCase(buyNodeCreated)) {
                String userAddress = "0x" + topics.get(1).toString().substring(2).replace("000000000000000000000000", "");
                BigDecimal rechargeAmount = new BigDecimal(new BigInteger(topics.get(2).toString().substring(2),16));
                Integer userLevel = new Integer(new BigInteger(topics.get(3).toString().substring(2),16).toString());
                /*测试数据*/
                tradeManager.evmRecharge(userAddress, rechargeAmount, userLevel, ChainTypeEnum.BSCTEST.getChainId(), RechargeStatusEnum.RECHARGE_SUCCESS.getStatus());
                //                测试数据BSCTESTNET
                //                测试数据GOERLI
                UpdateWrapper<EvmUserWallet> updateWrapper = Wrappers.update();
                updateWrapper.lambda()
                        .set(EvmUserWallet::getUserAddress, userAddress)
                        .set(EvmUserWallet::getUserLevel, userLevel)
                        .set(EvmUserWallet::getRechargeAmount, rechargeAmount)
                        .eq(EvmUserWallet::getUserId, appUserMapper.findUserIdByUserAddress(userAddress)).eq(EvmUserWallet::getChainId, ChainTypeEnum.BSCTEST.getChainId());
                evmUserWalletService.update(updateWrapper);
                walletsService.updateWallet(userAddress, rechargeAmount, FlowingActionEnum.INCOME, FlowingTypeEnum.RECHARGE, ChainTypeEnum.BSCTEST.getChainId());
                String blockHash = logsLog.getBlockHash();
                EthBlock.Block ethBlock = erc20BSCWalletHandleService.getBlockByHash(blockHash);
                Date date = new Date(ethBlock.getTimestamp().intValue() * 1000L);
            
                EvmEvent ethScanDataEntity = EvmEvent.builder()
                        .txHash(txHash)
                        .blockNum(new BigInteger(logsLog.getBlockNumber().toString(10)))
                        .createTime(date)
                        .chainId(ChainTypeEnum.BSC.getChainId())
                        .build();
                evmEventEntityArrayList.add(ethScanDataEntity);
            }
        }
        if (CollectionUtil.isNotEmpty(evmEventEntityArrayList)) {
            evmEventService.saveBatch(evmEventEntityArrayList);
        }
}}