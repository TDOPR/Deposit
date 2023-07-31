package com.pp.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Description 全局静态变量配置
 * @Author Dominick Li
 * @CreateTime 2022/10/20 17:08
 **/
@Component
public class GlobalProperties {
    private static long tokenExpire;
    
    private static String tokenSecret;
    
    private static String virtualPathURL;
    
    private static String rootPath;
    
    private static boolean prodEnv;
    

    public static String getVirtualPathURL() {
        return virtualPathURL;
    }
    
    @Value("${app.virtualPathURL}")
    public  void setVirtualPathURL(String virtualPathURL) {
        GlobalProperties.virtualPathURL = virtualPathURL;
    }
    
    public static long getTokenExpire() {
        return tokenExpire;
    }
    
    @Value("${jwt.expire}")
    public  void setTokenExpire(long tokenExpire) {
        GlobalProperties.tokenExpire = tokenExpire;
    }
    
    public static String getTokenSecret() {
        return tokenSecret;
    }
    
    @Value("${jwt.secret}")
    public  void setTokenSecret(String tokenSecret) {
        GlobalProperties.tokenSecret = tokenSecret;
    }
    
    public static String getRootPath() {
        return rootPath;
    }
    
    @Value("${app.rootPath}")
    public  void setRootPath(String rootPath) {
        GlobalProperties.rootPath = rootPath;
    }
    
    public static boolean isProdEnv() {
        return prodEnv;
    }
    
    @Value("${app.prodEnv:true}")
    public  void setProdEnv(boolean prodEnv) {
        GlobalProperties.prodEnv = prodEnv;
    }
}
