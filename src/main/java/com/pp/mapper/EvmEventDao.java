package com.pp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pp.model.EvmEvent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface EvmEventDao extends BaseMapper<EvmEvent> {
    int getCountByTxHash(@Param("txHash") String txHash);
}
