package com.pp.utils;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author Dominick Li
 * @description 自增Id基类
 **/
@Data
public class BaseModelCID {
    private static final long serialVersionUID = 2863256929817929824L;
    
    /**
     * 唯一标识
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    
    /**
     * 创建时间
     * @ignore
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    /**
     * 修改时间
     * @ignore
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime lastmodifiedTime;
}
