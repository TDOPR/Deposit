package com.pp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pp.model.AppUsers;
import jnr.ffi.annotations.In;
import org.apache.ibatis.annotations.Param;

public interface AppUserMapper extends BaseMapper<AppUsers> {
    Integer findUserIdByUserAddress(@Param("userAddress") String userAddress);
    
    String findUserAddressByUserId(@Param("userId") Integer userId);
}
