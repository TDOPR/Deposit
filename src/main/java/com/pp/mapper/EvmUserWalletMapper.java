package com.pp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pp.model.EvmUserWallet;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

public interface EvmUserWalletMapper extends BaseMapper<EvmUserWallet> {
    
    int setLevelByRecord(@Param("userAddress") String userAddress, @Param("userLevel") Integer userLevel, @Param("chainId") Integer chainId);
    Integer getUserLevel(@Param("userId") Integer userId);
    
    BigDecimal getRechargeAmount(@Param("userId") Integer userId);
}
