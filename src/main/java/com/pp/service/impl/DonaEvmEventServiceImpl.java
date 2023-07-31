package com.pp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pp.mapper.DonaEvmEventDao;
import com.pp.mapper.EvmEventDao;
import com.pp.model.DonaEvmEvent;
import com.pp.model.EvmEvent;
import com.pp.service.DonaEvmEventService;
import com.pp.service.EvmEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("DonaEvmEventService")
public class DonaEvmEventServiceImpl extends ServiceImpl<DonaEvmEventDao, DonaEvmEvent> implements DonaEvmEventService {
    @Autowired
    private DonaEvmEventDao donaEvmEventDao;

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
        int num = donaEvmEventDao.getCountByTxHash(txHash);
        return num > 0;
    }
}
