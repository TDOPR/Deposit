package com.pp.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pp.model.AppUsers;
import com.pp.model.dto.AppUserLoginDTO;
import com.pp.model.dto.AppUserRegisterDTO;
import com.pp.model.dto.PageDTO;
import com.pp.model.vo.AppTokenVO;
import com.pp.model.vo.PageVO;
import com.pp.model.vo.UserRecordVO;
import com.pp.utils.JsonResult;

public interface AppUserService extends IService<AppUsers> {
    JsonResult register(AppUserRegisterDTO appUserRegisterDTO);
    JsonResult<PageVO<UserRecordVO>> getRecord(String address, Page page);
    
    JsonResult<AppTokenVO> login(AppUserLoginDTO appUserLoginDTO, String localIp);
}
