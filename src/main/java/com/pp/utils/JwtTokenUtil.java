package com.pp.utils;

import com.pp.utils.redis.RedisUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.Date;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.function.Function;

/**
 * @author Dominick Li
 * @description java web token 配置类
 **/
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtTokenUtil {
    /**
     * 根据身份ID标识，生成Token
     */
    public static String getToken(Integer identityId, String username, String roleCode, Integer roleId) {
        Date nowDate = new Date();
        //过期时间
        Date expireDate = new Date(nowDate.getTime() + GlobalProperties.getTokenExpire() * 1000);
        return Jwts.builder()
                .setHeaderParam("type", "JWT")
                //放入唯一标识,可以是用户名或者Id
                .setSubject(identityId.toString())
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, GlobalProperties.getTokenSecret())
                //自定义属性 放入用户拥有请求权限
                .claim("userName", username)
                .claim("roleCode", roleCode)
                .claim("roleId", roleId)
                .compact();
    }
    
    /**
     * 根据身份ID标识，生成Token
     */
    public static String getToken(Integer identityId) {
        Date nowDate = new Date();
        //过期时间
        Date expireDate = new Date(nowDate.getTime() + GlobalProperties.getTokenExpire() * 1000);
        return Jwts.builder()
                .setHeaderParam("type", "JWT")
                //放入唯一标识,可以是用户名或者Id
                .setSubject(identityId.toString())
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, GlobalProperties.getTokenSecret())
                .compact();
    }
    
    /**
     * 根据token获取身份信息
     */
    public static Claims getTokenClaim(String token) {
        token = RedisUtil.getCacheObject(token);
        if (StringUtils.hasText(token)) {
            return Jwts.parser().setSigningKey(GlobalProperties.getTokenSecret()).parseClaimsJws(token).getBody();
        }
        return null;
    }
    
    
    /**
     * 根据token获取username
     */
    public static Integer getUserIdFromToken(String token) {
        return Integer.parseInt(getClaimFromToken(token, Claims::getSubject));
        
    }
    
    /**
     * 根据token获取角色编码
     */
    public static String getRoleCodeFromToken(String token) {
        return getTokenClaim(token).get("roleCode", String.class);
    }
    
    /**
     * 根据token获取角色Id
     */
    public static Integer getRoleIdFromToken(String token) {
        return getTokenClaim(token).get("roleId", Integer.class);
    }
    
    /**
     * 根据token获取用户名
     */
    public static String getUserNameFromToken(String token) {
        return getTokenClaim(token).get("userName", String.class);
    }
    
    /**
     * 判断token是否失效
     */
    public static boolean isTokenExpired(String token) {
        if (!StringUtils.hasText(token)) {
            return true;
        }
        token = RedisUtil.getCacheObject(token);
        if (StringUtils.hasText(token)) {
            return false;
        }
        return true;
    }
    
    
    public static <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getTokenClaim(token);
        return claimsResolver.apply(claims);
    }
}
