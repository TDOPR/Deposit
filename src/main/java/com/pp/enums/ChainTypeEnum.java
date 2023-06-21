package com.pp.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ChainTypeEnum {
    ETH(1),
    
//    /*测试*
    GOERLI(5),
    
    BSCTEST(97),
    
    TRON(0),
    BSC(56);
    
    private int chainId;
    
    public static ChainTypeEnum chainIdOf(int chainId) {
        for (ChainTypeEnum chainTypeEnum : values()) {
            if (chainTypeEnum.getChainId() == chainId) {
                return chainTypeEnum;
            }
        }
        return null;
    }
}
