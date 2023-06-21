package com.pp.model;

import com.baomidou.mybatisplus.annotation.*;
import com.pp.utils.BaseModelCID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Dominick Li
 * @Description
 * @CreateTime 2022/12/6 16:46
 **/
@Data
@Builder
@TableName("evm_user_wallet")
@NoArgsConstructor
@AllArgsConstructor
public class EvmUserWallet extends BaseModelCID {
    
    /**
     * 用户id
     */
    @TableField(value = "uid")
    private Integer userId;
    
    /**
     * 用户等级
     */
    @TableField(value = "user_level")
    private Integer userLevel;
    
    /**
     * 币种Id
     */
    @TableField(value = "chain_id")
    private Integer chainId;
    
    
    /**
     * 网络名称
     */
    @TableField(value = "coin_type")
    private String coinType;
    
    /**
     * 钱包地址
     */
    @TableField(value = "user_address")
    private String userAddress;
    
    /**
     * 充值金额
     */
    @TableField(value = "recharge_amount")
    private BigDecimal rechargeAmount;
    
    /**
     * 钱包地址
     */
    @TableField(value = "lower_address")
    private String lowerAddress;
    
    /**
     * keystore
     */
    private String keystore;
    
    /**
     * 秘钥
     */
    private String password;
    
    /**
     * 是否可用：E可用，D不可用
     */
    private String valid;
    
}
