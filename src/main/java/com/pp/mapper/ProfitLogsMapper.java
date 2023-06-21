package com.pp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pp.model.ProfitLogs;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface ProfitLogsMapper extends BaseMapper<ProfitLogs> {
//    /**
//     * 获取昨天的收益
//     */
//    BigDecimal getYesterday(@Param("userId")Integer userId);
//
//    /**
//     * 查看上周的用户收益
//     */
//    BigDecimal getLastWeek(Integer userId);
//
//    /**
//     * 查看上月的用户收益
//     */
//    BigDecimal getLastMonth(Integer userId);
//
//    /**
//     * 查看用户收益的总收益
//     */
//    BigDecimal getTotal(Integer userId);
//
//    /**
//     * 根据类型获取用户的量化总收益
//     */
//    BigDecimal getTotalAmountByUserIdAndType(@Param("userId") Integer userId, @Param("type")Integer type);
//
//    /**
//     * 根据日期查询当天的量化总收益
//     */
//    BigDecimal getSumProfitByCreateDate(LocalDate localDate);
}
