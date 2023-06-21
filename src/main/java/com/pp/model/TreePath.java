package com.pp.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@TableName("tree_paths")
@NoArgsConstructor
@AllArgsConstructor
public class TreePath {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 父
     */
    private Integer ancestor;
    /**
     * 子
     */
    private Integer descendant;
    /**
     * 子是父的几级
     */
    private Integer level;
    /**
     * 是否为大团队 (该字段只对直推一代有效) 1=大团队 0=小团队
     */
    private Integer largeTeam;
    
}
