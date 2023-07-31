package com.pp.service.impl;


import cn.hutool.http.HttpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pp.config.ChainIdsConfig;
import com.pp.enums.ChainTypeEnum;
import com.pp.enums.ReturnMessageEnum;
import com.pp.mapper.AppUserMapper;
import com.pp.mapper.WalletLogsMapper;
import com.pp.mapper.WalletsMapper;
import com.pp.model.*;
import com.pp.model.dto.AppUserLoginDTO;
import com.pp.model.dto.AppUserRegisterDTO;
import com.pp.model.dto.BindMailDTO;
import com.pp.model.dto.PageDTO;
import com.pp.model.vo.AppTokenVO;
import com.pp.model.vo.PageVO;
import com.pp.model.vo.UserRecordVO;
import com.pp.service.*;
import com.pp.utils.GlobalProperties;
import com.pp.utils.IdUtil;
import com.pp.utils.JsonResult;
import com.pp.utils.JwtTokenUtil;
import com.pp.utils.config.SysSettingParam;
import com.pp.utils.constant.CacheKeyPrefixConstants;
import com.pp.mapper.SysLoginLogMapper;
import com.pp.utils.model.SysLoginLog;
import com.pp.utils.redis.RedisUtil;
import com.pp.utils.service.SysLoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
public class AppUserServiceImpl extends ServiceImpl<AppUserMapper, AppUsers> implements AppUserService {
    
    @Autowired
    private TreePathService treePathService;
    
    @Resource
    private WalletsService walletsService;
    
    @Autowired
    private WalletLogsService walletsLogService;
    
    @Autowired
    private SysLoginLogService sysLoginLogService;
    
    @Resource
    private SysLoginLogMapper sysLoginLogMapper;
    
    @Autowired
    private EvmUserWalletService evmUserWalletService;
    
    @Resource
    private WalletLogsMapper walletLogsMapper;
    
    @Resource
    private  AppUserMapper appUserMapper;
    
    @Autowired
    ChainIdsConfig chainIdsConfig;
    
    @Autowired
    AppDonaUserService appDonaUserService;
    
    @Override
    public JsonResult<AppTokenVO> login(AppUserLoginDTO appUserLoginDTO, String localIp) {
//        EvmUserWallet evmUserWallet = evmUserWalletService.getOne(
//                new LambdaQueryWrapper<EvmUserWallet>()
//                        .eq(EvmUserWallet::getUserAddress, appUserLoginDTO.getUserAddress())
//                        .eq(EvmUserWallet::getChainId, appUserLoginDTO.getChainId())
//        );
        
        AppUsers appUsers = this.getOne(
                new LambdaQueryWrapper<AppUsers>().eq(AppUsers::getUserAddress, appUserLoginDTO.getUserAddress())
        );
        if (appUsers == null) {
            return JsonResult.failureResult(ReturnMessageEnum.ADDRESS_NOT_EXISTS);
        }
        
//        //修改登录次数
//        UpdateWrapper<AppUsers> updateWrapper = Wrappers.update();
//        updateWrapper.lambda()
//                .set(AppUsers::getLoginCount, appUsers.getLoginCount() + 1)
//                .eq(AppUsers::getId, appUsers.getId());
//        this.update(updateWrapper);
        
        String token = JwtTokenUtil.getToken(appUsers.getId());
        
        
        
        //单点登录需要删除用户在其它地方登录的Token
        if (SysSettingParam.isEnableSso()) {
            RedisUtil.deleteObjects(CacheKeyPrefixConstants.APP_TOKEN + appUsers.getId() + ":*");
        }
        
        //把token存储到缓存中
        String tokenKey = CacheKeyPrefixConstants.APP_TOKEN + appUsers.getId() + ":" + IdUtil.simpleUUID();
        RedisUtil.setCacheObject(tokenKey, token, Duration.ofSeconds(GlobalProperties.getTokenExpire()));
        sysLoginLogService.save(new SysLoginLog(appUsers.getUserAddress(), localIp, 2));
        
        //返回token给客户端
        AppTokenVO appTokenVO = new AppTokenVO();
        appTokenVO.setToken(tokenKey);
        appTokenVO.setInviteCode(appUsers.getInviteCode());
        return JsonResult.successResult(appTokenVO);
    }
    
    @Override
    @Transactional
    public JsonResult register(AppUserRegisterDTO appUserRegisterDTO) {
        AppUsers inviteUser;
        AppUsers user;
        user = this.getOne(new LambdaQueryWrapper<AppUsers>().eq(AppUsers::getUserAddress, appUserRegisterDTO.getUserAddress()));
        inviteUser =this.getOne(new LambdaQueryWrapper<AppUsers>().eq(AppUsers::getInviteCode, appUserRegisterDTO.getInviteCode()));
        if(inviteUser == null){
            return JsonResult.failureResult(ReturnMessageEnum.INVITE_CODE_ERROR);
        }else if(user != null){
            return JsonResult.failureResult(ReturnMessageEnum.ACCOUNT_EXISTS);
        } else{
            AppUsers appUsers = new AppUsers();
            appUsers.setUserAddress(appUserRegisterDTO.getUserAddress().toLowerCase());
            appUsers.setInviteCode(getInviteCode());
            appUsers.setInviteId(inviteUser.getId());
            this.save(appUsers);
            /*测试*/
//            switch (appUserRegisterDTO.getChainId()) {
//                case 5:
//                    EvmUserWallet evmUserWalletETH = new EvmUserWallet();
//                    evmUserWalletETH.setUserId(appUsers.getId());
//                    evmUserWalletETH.setChainId(ChainTypeEnum.GOERLI.getChainId());
//                    evmUserWalletService.save(evmUserWalletETH);
//                    break;
//                case 97:
//                    EvmUserWallet evmUserWalletBSC = new EvmUserWallet();
//                    evmUserWalletBSC.setUserId(appUsers.getId());
//                    evmUserWalletBSC.setChainId(ChainTypeEnum.BSCTEST.getChainId());
//                    evmUserWalletService.save(evmUserWalletBSC);
//                    break;
//                case 0:
//                    EvmUserWallet evmUserWalletTRON = new EvmUserWallet();
//                    evmUserWalletTRON.setUserId(appUsers.getId());
//                    evmUserWalletTRON.setChainId(ChainTypeEnum.TRON.getChainId());
//                    evmUserWalletService.save(evmUserWalletTRON);
//                    break;
//                default:
//                    break;
//            }
            EvmUserWallet evmUserWalletETH = new EvmUserWallet();
            EvmUserWallet evmUserWalletBSC = new EvmUserWallet();
            EvmUserWallet evmUserWalletTRON = new EvmUserWallet();
            evmUserWalletETH.setUserId(appUsers.getId());
            evmUserWalletETH.setUserLevel(Integer.valueOf(0));
            evmUserWalletETH.setChainId(chainIdsConfig.getEth());
            evmUserWalletETH.setRechargeAmount(BigDecimal.ZERO);
            evmUserWalletService.save(evmUserWalletETH);
            evmUserWalletBSC.setUserId(appUsers.getId());
            evmUserWalletBSC.setUserLevel(Integer.valueOf(0));
            evmUserWalletBSC.setChainId(chainIdsConfig.getBsc());
            evmUserWalletBSC.setRechargeAmount(BigDecimal.ZERO);
            evmUserWalletService.save(evmUserWalletBSC);
            evmUserWalletTRON.setUserId(appUsers.getId());
            evmUserWalletTRON.setUserLevel(Integer.valueOf(0));
            evmUserWalletTRON.setChainId(chainIdsConfig.getTron());
            evmUserWalletTRON.setRechargeAmount(BigDecimal.ZERO);
            evmUserWalletService.save(evmUserWalletTRON);
            //创建一条钱包记录
            Wallets wallets = new Wallets();
            wallets.setUserId(appUsers.getId());
            walletsService.save(wallets);
            //添加一条默认的treepath记录
            TreePath treePath =TreePath.builder()
                    .ancestor(appUsers.getId())
                    .descendant(appUsers.getId())
                    .level(0)
                    .build();
            treePathService.save(treePath);
            if (appUsers.getInviteId() != null) {
                //保存上下级关系
                treePathService.insertTreePath(appUsers.getId(), appUsers.getInviteId());
            }
            return JsonResult.successResult();
        }
    }
    
    @Override
    public JsonResult<PageVO<UserRecordVO>> getRecord(String address, Page page){
        Page<UserRecordVO> recordPrize = walletLogsMapper.getPrizeRecord(page, appUserMapper.findUserIdByUserAddress(address));
        return JsonResult.successResult(new PageVO<>(recordPrize));
    }
    
    /**
     * 生成唯一邀请码
     */
    private String getInviteCode() {
        String inviteCode = IdUtil.generateShortUUID(8);
        AppUsers exists;
        while (true) {
            exists = this.getOne(new LambdaQueryWrapper<AppUsers>().eq(AppUsers::getInviteCode, inviteCode));
            if (exists != null) {
                inviteCode = IdUtil.generateShortUUID(8);
            } else {
                break;
            }
        }
        return inviteCode;
    }
    
    @Override
    public JsonResult bindMail(BindMailDTO bindMailDTO){
        
        if (GlobalProperties.isProdEnv()) {
            String cacheKey = CacheKeyPrefixConstants.CAPTCHA_CODE + bindMailDTO.getUuid();
            String code = RedisUtil.getCacheObject(cacheKey);
            if (code == null) {
                return JsonResult.failureResult(ReturnMessageEnum.VERIFICATION_CODE_EXPIRE);
            }
            if (!code.equals(bindMailDTO.getCode())) {
                return JsonResult.failureResult(ReturnMessageEnum.VERIFICATION_CODE_ERROR);
            }
        }
        AppUsers appUsers;
        appUsers = this.getOne(new LambdaQueryWrapper<AppUsers>().eq(AppUsers::getEmail, bindMailDTO.getEmail()));
        if(appUsers == null){
            UpdateWrapper<AppUsers> updateWrapper = Wrappers.update();
            updateWrapper.lambda()
                    .set(AppUsers::getEmail, bindMailDTO.getEmail())
                    .eq(AppUsers::getId, JwtTokenUtil.getUserIdFromToken(ThreadLocalManager.getToken()));
            boolean flag = this.update(updateWrapper);
            appDonaUserService.registerDonaUser(bindMailDTO);
            return JsonResult.build(flag);
        }else {
            return JsonResult.failureResult(ReturnMessageEnum.EMAIL_EXISTS);
        }
    }
    
}
