package com.pp.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.pp.utils.BaseModel;
import lombok.Data;

/**
 * @author Dominick Li
 * @Description 用户接取的任务
 * @CreateTime 2023/2/24 15:20
 **/
@Data
@TableName("certificate")
public class Certificate extends BaseModel {
    
    
    
    /**
     * 所属用户Id
     */
    private Integer userId;

    /**
     * url
     */
    private String url;
    
    
    /**
     * 文件夹路径
     */
    private String folderPath;
    
    /**
     * 等级
     */
    private Integer level;
    

}
