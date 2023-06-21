package com.pp.model.dto;


import lombok.Data;

/**
 * @author Dominick Li
 * @Description
 * @CreateTime 2023/1/8 11:02
 **/
@Data
public class TreePathLevelDTO {
    
    /**
     * 上级用户Id
     */
    private Integer userId;
    
    /**
     * 代理商等级
     */
    private Integer userLevel;
}
