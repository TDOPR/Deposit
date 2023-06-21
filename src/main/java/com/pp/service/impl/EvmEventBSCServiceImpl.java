package com.pp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pp.model.EvmEvent;
import com.pp.mapper.EvmEventDao;
import com.pp.service.EvmBSCEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("EvmBSCEventService")
public class EvmEventBSCServiceImpl extends ServiceImpl<EvmEventDao, EvmEvent> implements EvmBSCEventService {
    @Autowired
    private EvmEventDao evmEventDao;

////    @Override
//    public PageUtils queryPage(Map<String, Object> params) {
//        IPage<EvmEventEntity> page = this.page(
//                new Query<EvmEventEntity>().getPage(params),
//                new QueryWrapper<EvmEventEntity>()
//        );
//
//        return new PageUtils(page);
//    }
    
    @Override
    public boolean getEventExistByTxHash(String txHash) {
        int num = evmEventDao.getCountByTxHash(txHash);
        return num > 0;
    }
}
