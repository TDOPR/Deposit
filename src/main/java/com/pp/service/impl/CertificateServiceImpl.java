package com.pp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pp.enums.DataSavePathEnum;
import com.pp.mapper.CertificateMapper;
import com.pp.model.Certificate;
import com.pp.service.CertificateService;
import com.pp.utils.*;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.time.LocalDateTime;

/**
 * @author Dominick Li
 * @Description
 * @CreateTime 2023/5/16 11:23
 **/
@Service
public class CertificateServiceImpl extends ServiceImpl<CertificateMapper, Certificate> implements CertificateService {

    @Resource
    private AppParamProperties appParamProperties;
    
    @Override
    public JsonResult generateCertificate(Integer userId, String name, String number, String level, LocalDateTime date){
        try{
            Certificate certificate = new Certificate();
            certificate.setUserId(userId);
            certificate.setLevel(Integer.valueOf(level));
            DataSavePathEnum dataSavePathEnum = DataSavePathEnum.CERTIFICATE;
            String saveFileName = IdUtil.simpleUUID() + "." + appParamProperties.getSuffix();
            File saveFile = new File(dataSavePathEnum.getFile(), saveFileName);
            FileInputStream fileInputStream = new FileInputStream(new File(appParamProperties.getSourceFile()));
//            String number = myIncrementGenerator.usingMath(CarbonConfig.STRING_LENGTH);
            InputStream result = ThumbnailsService.addWaterMark(fileInputStream, level, name, number, String.valueOf(date));
            FileUtils.copyInputStreamToFile(result, saveFile);
            String url = GlobalProperties.getVirtualPathURL() + StringUtil.replace(dataSavePathEnum.getPath(), GlobalProperties.getRootPath(), "") + saveFileName;
            certificate.setFolderPath(saveFile.getAbsolutePath());
            certificate.setUrl(url);
            return JsonResult.successResult();
        } catch (Exception e){
            log.error("banner: saveAndUpload error:{}", e);
            return JsonResult.failureResult();
        }
        
    }
}
