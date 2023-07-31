package com.pp.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Dominick Li
 * @Description 收支类型
 * @CreateTime 2022/11/1 10:30
 **/
@Getter
@AllArgsConstructor
public enum FlowingActionEnum {
    INCOME(1, "收入"),
    EXPENDITURE(0, "支出"),
    TRANSFER(2,"转入")
    ;
    private Integer value;
    private String name;
}
