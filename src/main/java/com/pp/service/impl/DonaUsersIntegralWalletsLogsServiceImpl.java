package com.pp.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pp.mapper.DonaUsersIntegralWalletsLogsMapper;
import com.pp.model.DonaUsersIntegralLogs;
import com.pp.service.DonaUsersIntegralWalletsLogsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Dominick Li
 * @Description
 * @CreateTime 2022/11/1 18:54
 **/
@Slf4j
@Service
public class DonaUsersIntegralWalletsLogsServiceImpl extends ServiceImpl<DonaUsersIntegralWalletsLogsMapper, DonaUsersIntegralLogs> implements DonaUsersIntegralWalletsLogsService {
    
    @Resource
    private DonaUsersIntegralWalletsLogsMapper donaUsersIntegralWalletsLogsMapper;
    
    
//    @Override
//    public JsonResult<PageVO<PaticiTaskDTO>> carbonFootprint(Page page){
//        Integer userId = JwtTokenUtil.getUserIdFromToken(ThreadLocalManager.getToken());
//        List<PaticiTaskDTO> paticiTask = donaUsersIntegralWalletsLogsMapper.particiTask(userId);
//        Page<PaticiTaskDTO> paticiTaskDTOPage = donaUsersIntegralWalletsLogsMapper.task(page);
//        for(PaticiTaskDTO taskId : paticiTaskDTOPage.getRecords()){
//            if(paticiTask.contains(taskId)){
//                taskId.setStatus(1);
////                json.put(taskId.getId().toString(), taskId.getTaskName() + "," + taskId.getTaskIntegral() + ", 1");
//            } else {
//                taskId.setStatus(0);
//            }
//        }
//        return JsonResult.successResult(new PageVO<>(paticiTaskDTOPage));
//    }
    
//    public List<PaticiTaskDTO> getDataList(int pageNum, int pageSize) {
//        // 模拟从数据库中查询分页数据
//        List<PaticiTaskDTO> list = new ArrayList<>();
//        for (int i = 0; i < pageSize; i++) {
//            list.add(new PaticiTaskDTO((pageNum - 1) * pageSize + i)); // 生成模拟数据
//        }
//        return list;
//    }
}
