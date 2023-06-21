package com.pp.model.condition;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.Data;

@Data
public class EvmTokenRechargeCondition {
    /**
     * 币种Id
     */
    private Integer chainId;
    
    /**
     * 用户邮箱号
     */
    private Integer status;
    
    /**
     * 钱包地址
     */
    private String address;
    
}
