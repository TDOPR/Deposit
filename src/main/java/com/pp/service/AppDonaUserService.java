package com.pp.service;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pp.model.AppDonaUsers;
import com.pp.model.dto.AppDonaUserRegisterDTO;
import com.pp.model.dto.BindMailDTO;
import com.pp.utils.JsonResult;

public interface AppDonaUserService extends IService<AppDonaUsers> {
    
//        JsonResult login(AppDonaUserLoginDTO appUserLoginDTO, String localIp);

        void registerDonaUser(BindMailDTO bindMailDTO);
//        JsonResult findPassword(FindPasswordDTO findPasswordDTO);
//        JsonResult modifyUserName(AppDonaUserDTO appUserDTO);
        
//        JsonResult uploadHeadImage(MultipartFile file) throws Exception;
        
//        JsonResult updatePassword(UpdatePasswordDTO updatePasswordDTO);
        
        AppDonaUsers selectColumnsByUserId(Integer userId, SFunction<AppDonaUsers, ?>... columns);
        
//        JsonResult loginOut();
//
//        JsonResult updatePassword(UpdatePasswordDTO updatePasswordDTO);

}
