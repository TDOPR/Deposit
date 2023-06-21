package com.pp.scheduled;

import com.pp.manager.EventManager;
import com.pp.utils.annotation.RedisLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * @author Dominick Li
 * @Description 区块链事务拉取类
 * @CreateTime 2022/11/11 17:48
 **/
@Slf4j
@Component
public class PullEventScheduledJob {
    
    @Autowired
    private EventManager eventManager;
    
    /**
     * 区块链 每隔3秒拉取事务
     */
    @Scheduled(fixedDelay = 6000)
    @RedisLock
    public void analyzeETHEvent() throws Exception{
    
        eventManager.analyzeETHEvent();
    }
    
    @Scheduled(fixedDelay = 3000)
    @RedisLock
    public void analyzeBSCEvent() throws Exception{
        
        eventManager.analyzeBSCEvent();
    }
}