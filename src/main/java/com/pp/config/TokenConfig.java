package com.pp.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "token")
public class TokenConfig {
    
    /**
     * web3Eth请求地址
     */
    private String web3EthServiceUrl;
    /**
     * web3Bsc请求地址
     */
    private String web3BscServiceUrl;
    /**
     * web3Tron请求地址
     */
    private String web3TronServiceUrl;
    
    /**
     * 钱包账号
     */
    private String web3Wallet;
    
    /**
     * 链id数组
     */
    private List<Integer> chainIds;
    
}
