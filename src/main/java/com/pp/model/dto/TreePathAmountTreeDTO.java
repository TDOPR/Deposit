package com.pp.model.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Dominick Li
 * @Description 下级收益信息以及基本
 * @CreateTime 2022/11/14 18:44
 **/
@Data
public class TreePathAmountTreeDTO {
    /**
     * 托管本金
     */
    private BigDecimal principalAmount;
    
    /**
     * 静态收益
     */
    private BigDecimal generatedAmount;
    
    /**
     * 当前用户Id
     */
    private Integer descendant;
    
    /**
     * 级别
     */
    private Integer level;
    
    /**
     * 下级收益
     */
    private List<TreePathAmountTreeDTO> childList;
}
