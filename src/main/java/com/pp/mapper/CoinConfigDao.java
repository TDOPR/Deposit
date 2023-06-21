package com.pp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pp.model.CoinConfig;
import org.apache.ibatis.annotations.Param;

import java.math.BigInteger;

public interface CoinConfigDao extends BaseMapper<CoinConfig> {
    CoinConfig getScanDataConfig();
    CoinConfig getBscScanDataConfig();
    CoinConfig getEthDepositScanDataConfig();
    CoinConfig getBscDepositScanDataConfig();
    int updateActionSeqById(@Param("id") Integer id, @Param("blockNo") BigInteger blockNo);
}
