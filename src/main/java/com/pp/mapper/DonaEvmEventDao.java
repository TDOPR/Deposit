package com.pp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pp.model.DonaEvmEvent;
import com.pp.model.EvmEvent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DonaEvmEventDao extends BaseMapper<DonaEvmEvent> {
    int getCountByTxHash(@Param("txHash") String txHash);
}
