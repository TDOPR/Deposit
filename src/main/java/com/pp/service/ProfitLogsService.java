package com.pp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pp.model.ProfitLogs;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ProfitLogsService extends IService<ProfitLogs> {
    /**
     * 修改收益信息表字段为已发放
     *
     * @param idList
     * @return
     */
    boolean updateUseByIdList(List<Long> idList);
    
//    /**
//     * 查询量化收益明细
//     *
//     * @param userId 用户Id
//     * @return
//     */
//    MyWalletsVO.Quantification getMyQuantification(Integer userId);
//
//    /**
//     * 根据用户和交割类型查询总金额
//     * @param userId 用户Id
//     * @param type 类型
//     * @return 统计的总基恩
//     */
//    BigDecimal getTotalAmountByUserIdAndType(Integer userId, Integer type);
    
    /**
     * 查询指定日期的所有量化收益
     * @param createDate 日期
     * @return 金额
     */
    BigDecimal getSumProfitByCreateDate(LocalDate createDate);
}
