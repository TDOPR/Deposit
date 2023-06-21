package com.pp.model.dto;

import lombok.Data;

@Data
public class AppUserRegisterDTO extends FindPasswordDTO {
    /**
     * 邀请码
     */
    private String inviteCode;
    
    /**
     * 用户地址
     */
    private String userAddress;
    
    /**
     * 链ID
     */
    private Integer chainId;
}
