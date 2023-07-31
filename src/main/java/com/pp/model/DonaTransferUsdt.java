package com.pp.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 充值记录
 */
@Data
@Builder
@TableName("dona_transfer_usdt")
@NoArgsConstructor
@AllArgsConstructor
public class DonaTransferUsdt {
    /**
     * 唯一标识
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 用户邮箱
     */
    private String email;
    
    
    /**
     * 链Id
     */
    @TableField(value = "chain_id")
    private Integer chainId;
    
    
    /**
     * 钱包地址
     */
    private String address;
    
    
    
    /**
     * 实际充值金额
     */
    private BigDecimal donaTransferUsdtAmount;
    
    
    /**
     * 创建时间
     */
    @TableField(value = "created", fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    /**
     * 修改时间
     */
    @TableField(value = "last_update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime lastmodifiedTime;
    
}
