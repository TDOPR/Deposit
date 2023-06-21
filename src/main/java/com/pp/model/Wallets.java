package com.pp.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.pp.utils.BaseModel;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Dominick Li
 * @Description 用户钱包
 * @CreateTime 2022/11/1 10:57
 **/
@Data
@TableName("wallets")
public class Wallets extends BaseModel {
    /**
     * 所属用户Id
     */
    private Integer userId;
    
    /**
     * 机器人购买金额
     */
    private BigDecimal robotAmount;
    
    /**
     * 用户等级
     */
    private Integer userLevel;
    
    /**
     * 用户地址
     */
    private String userAddress;
    
    /**
     * 链ID
     */
    private Integer chainId;
    
    /**
     * 机器人等级
     */
    private Integer robotLevel;
    
    
    /**
     * 法币账号
     */
    private String legalCurrencyAccount;
    
    /**
     * 钱包余额
     */
    private BigDecimal walletAmount;
    
    /**
     * 托管本金
     */
    private BigDecimal principalAmount;
    
    /**
     * 提现冻结的金额
     */
    private BigDecimal frozenAmount;
}
