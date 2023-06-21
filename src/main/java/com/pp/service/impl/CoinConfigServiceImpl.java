package com.pp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pp.model.CoinConfig;
import com.pp.mapper.CoinConfigDao;
import com.pp.service.CoinConfigService;
import org.springframework.stereotype.Service;

@Service
public class CoinConfigServiceImpl extends ServiceImpl<CoinConfigDao, CoinConfig> implements CoinConfigService {
}
