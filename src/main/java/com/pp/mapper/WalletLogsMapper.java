package com.pp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pp.model.WalletLogs;
import com.pp.model.vo.UserRecordVO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface WalletLogsMapper extends BaseMapper<WalletLogs> {
    
    Page<UserRecordVO> getPrizeRecord(Page page, @Param("userId") Integer userId);

}
