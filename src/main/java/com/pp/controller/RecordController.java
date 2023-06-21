package com.pp.controller;

import com.pp.mapper.WalletsMapper;
import com.pp.model.dto.PageDTO;
import com.pp.model.dto.RecordDTO;
import com.pp.model.vo.PageVO;
import com.pp.model.vo.UserRecordVO;
import com.pp.service.AppUserService;
import com.pp.utils.JsonResult;
import com.pp.utils.annotation.PrintLog;
import com.pp.utils.annotation.RepeatSubmit;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
public class RecordController {
    
    @Resource
    private AppUserService appUserService;
    
    @Resource
    private WalletsMapper walletsMapper;
    
    /**
     * 奖励记录
     */
    @PrintLog
    @RepeatSubmit
    @PostMapping("/record/{userAddress}")
    public JsonResult<PageVO<UserRecordVO>> record(@PathVariable String userAddress, @RequestBody PageDTO pageDTO) {
        return appUserService.getRecord(userAddress, pageDTO.getPage());
    }
}
