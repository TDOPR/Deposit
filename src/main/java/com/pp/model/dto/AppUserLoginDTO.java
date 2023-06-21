package com.pp.model.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AppUserLoginDTO {
    
    /**
     * 用户地址
     * @required
     */
    @NotEmpty
    private String userAddress;
    
    
}
