package com.pp.model.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AppUserAmountVO {
    
    
    /**
     * 全网用户数信息
     */
    private Integer userAmount;
    
    /**
     * 全网TVL
     */
    private BigDecimal userTotalAmount;
    
    /**
     * 全网Online用户数
     */
    private Integer userOnlineAmount;
}