package com.pp.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Dominick Li
 * @Description 提现的货币类型  网络来源
 * @CreateTime 2022/11/1 16:01
 **/
@Getter
@AllArgsConstructor
public enum CoinUnitEnum {
    USDT(1, "USDT"),
    FIAT(2, "FIAT");
    
    /**
     * 类型id
     */
    private Integer Id;
    
    /**
     * 货币名称
     */
    private String name;
    
    
    public static CoinUnitEnum idOf(Integer id) {
        for (CoinUnitEnum coinUnitEnum : values()) {
            if (coinUnitEnum.getId().equals(id)) {
                return coinUnitEnum;
            }
        }
        return null;
    }
    
    public static String getNameById(Integer id) {
        for (CoinUnitEnum coinUnitEnum : values()) {
            if (coinUnitEnum.getId().equals(id)) {
                return coinUnitEnum.getName();
            }
        }
        return "";
    }
}
