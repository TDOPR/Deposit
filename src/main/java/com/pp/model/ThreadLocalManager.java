package com.pp.model;

import com.pp.enums.LanguageEnum;
import com.pp.model.dto.HeaderInfo;

/**
 * @author Dominick Li
 * @Description 用于存储请求上下文信息
 * @CreateTime 2022/12/8 17:54
 **/
public class ThreadLocalManager {
    
    /**
     * 存储请求头信息
     */
    private final static ThreadLocal<HeaderInfo> headerInfoThreadLocal = new ThreadLocal<>();
    
    public static void add(HeaderInfo headerInfo) {
        headerInfoThreadLocal.set(headerInfo);
    }
    
    public static String getToken() {
        return headerInfoThreadLocal.get().getToken();
    }
    
    public static String getLanguage() {
        return headerInfoThreadLocal.get() != null ? headerInfoThreadLocal.get().getLanguage() : LanguageEnum.ZH_CH.getName();
    }
    
    public static String getHeaderInfo() {
        return headerInfoThreadLocal.get().getLanguage();
    }
    
    /**
     * 释放资源
     */
    public static void remove() {
        headerInfoThreadLocal.remove();
    }
}
