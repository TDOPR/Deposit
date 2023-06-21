package com.pp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pp.model.TreePath;
import org.apache.ibatis.annotations.Param;

public interface TreePathMapper extends BaseMapper<TreePath> {
    
    int insertTreePath(int uid,@Param("pid") int pid);
}
