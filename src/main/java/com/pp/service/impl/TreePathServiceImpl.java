package com.pp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pp.mapper.TreePathMapper;
import com.pp.model.TreePath;
import com.pp.service.TreePathService;
import org.springframework.stereotype.Service;

@Service
public class TreePathServiceImpl extends ServiceImpl<TreePathMapper, TreePath> implements TreePathService {
    
    
    @Override
    public void insertTreePath(Integer id, Integer inviteUserId) {
        this.baseMapper.insertTreePath(id, inviteUserId);
    }
}
