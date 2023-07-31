package com.pp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pp.model.Certificate;
import com.pp.utils.JsonResult;

import java.time.LocalDateTime;

/**
 * @author Dominick Li
 * @Description
 * @CreateTime 2023/5/16 11:23
 **/
public interface CertificateService extends IService<Certificate>  {
    
    JsonResult generateCertificate(Integer userId, String name, String number, String Level, LocalDateTime date);

}
