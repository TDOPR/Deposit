package com.pp.controller;

import com.pp.mapper.WalletsMapper;
import com.pp.model.dto.AppUserLoginDTO;
import com.pp.model.dto.AppUserRegisterDTO;
import com.pp.model.dto.RecordDTO;
import com.pp.model.vo.AppTokenVO;
import com.pp.model.vo.AppUserAmountVO;
import com.pp.service.AppUserService;
import com.pp.utils.IpAddrUtil;
import com.pp.utils.JsonResult;
import com.pp.utils.annotation.PrintLog;
import com.pp.utils.annotation.RepeatSubmit;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.math.BigDecimal;

import static com.pp.utils.RandomUtil.generateOnlineUserAmount;

@RestController
@RequestMapping("/home")
public class HomeController {
    
    @Resource
    private AppUserService appUserService;
    
    @Resource
    private WalletsMapper walletsMapper;
    
    /**
     * 注册
     */
    @PrintLog
    @RepeatSubmit
    @PostMapping("/register")
    public JsonResult register(@Valid @RequestBody AppUserRegisterDTO appUserRegisterDTO) {
        return appUserService.register(appUserRegisterDTO);
    }
    
    /**
     * 登录
     */
    @PrintLog
    @RepeatSubmit
    @PostMapping("/login")
    public JsonResult<AppTokenVO> login(@Valid @RequestBody AppUserLoginDTO appUserLogin, HttpServletRequest request) {
        return appUserService.login(appUserLogin, IpAddrUtil.getLocalIp(request));
    }
    
//    /**
//     * 奖励记录
//     */
//    @PrintLog
//    @RepeatSubmit
//    @PostMapping("/record")
//    public JsonResult record(@Valid @RequestBody RecordDTO recordDTO) {
//        return appUserService.getRecord(recordDTO.getAddress());
//    }
    
    /**
     * TVL
     */
    @PrintLog
    @RepeatSubmit
    @PostMapping("/tvl")
    public JsonResult<AppUserAmountVO> getTvl() {
        Integer result;
        AppUserAmountVO appUserAmountVO = walletsMapper.getPlatformTotalLockAmount();
        Integer userTotalAmount = appUserAmountVO.getUserAmount();
        if(userTotalAmount.compareTo(Integer.valueOf(5))<0){
            BigDecimal randomNum = generateOnlineUserAmount(0, 1);
            result = userTotalAmount - randomNum.intValue();
        }else if(userTotalAmount.compareTo(Integer.valueOf(5))>-1&&userTotalAmount.compareTo(Integer.valueOf(20))<0){
            BigDecimal randomNum = generateOnlineUserAmount(0, 3);
            result = userTotalAmount - randomNum.intValue();
        }else if(userTotalAmount.compareTo(Integer.valueOf(20))>-1&&userTotalAmount.compareTo(Integer.valueOf(100))<0){
            BigDecimal randomNum = generateOnlineUserAmount(0, 10);
            result = userTotalAmount - randomNum.intValue();
        }else{
            BigDecimal randomNum = generateOnlineUserAmount(0, 30);
            result = userTotalAmount - randomNum.intValue();
        }
        
        appUserAmountVO.setUserOnlineAmount(result);
        return JsonResult.successResult(appUserAmountVO);
    }
}
