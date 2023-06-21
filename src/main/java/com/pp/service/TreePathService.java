package com.pp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pp.model.TreePath;

public interface TreePathService extends IService<TreePath> {
    
    /**
     * 插入供应商 团队数据
     * @param userId 用户Id
     * @param inviteUserId 邀请人的用户Id
     */
    void insertTreePath(Integer userId, Integer inviteUserId);
}
