package com.pp.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.pp.utils.BaseModel;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Dominick Li
 * @Description 用户钱包
 * @CreateTime 2022/11/1 10:57
 **/
@Data
@TableName("dona_users_wallets")
public class DonaUsersWallets extends BaseModel {

    /**
     * 所属用户Id
     */
    private Integer userId;
    /**
     * 用户积分余额
     */
    private BigDecimal userIntegralAmount;
    /**
     * USD账户余额
     */
    private BigDecimal usdWalletAmount;
    /**
     * TTT钱包余额
     */
    private BigDecimal walletAmount;
    /**
     * 提现冻结的USD金额
     */
    private BigDecimal frozenAmount;
    /**
     * 已购买的任务总次数
     */
    private Long totalTaskNum;
    /**
     * 剩余可使用次数
     */
    private Long hasTaskNum;

}
