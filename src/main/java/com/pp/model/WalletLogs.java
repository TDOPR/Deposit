package com.pp.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.pp.utils.BaseModelNoModifyTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author Dominick Li
 * @Description 用户钱包流水表
 * @CreateTime 2022/11/1 10:57
 **/
@Data
@Builder
@TableName("wallet_logs")
@NoArgsConstructor
@AllArgsConstructor
public class WalletLogs extends BaseModelNoModifyTime {
    /**
     * 用户Id
     */
    private Integer userId;
    
    /**
     * 用户地址
     */
    private String userAddress;
    
    /**
     * 下级Id
     */
    private Integer subId;
    
    /**
     * 下级用户等级
     */
    private String subUserAddress;
    
    /**
     * 下级用户等级
     */
    private Integer subUserLevel;
    
    /**
     * 本次变动金额
     */
    private BigDecimal amount;
    
    /**
     * 收支类型 1=收入 2=支出
     */
    private Integer action;
    
    /**
     * 流水类型 对应FlowingTypeEnum枚举
     */
    private Integer type;
}
