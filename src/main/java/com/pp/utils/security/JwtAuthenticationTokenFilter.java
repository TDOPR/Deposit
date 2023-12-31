package com.pp.utils.security;

import com.alibaba.fastjson.JSONObject;
import com.pp.enums.ReturnMessageEnum;
import com.pp.utils.JsonResult;
import com.pp.utils.JwtTokenUtil;
import com.pp.utils.MessageUtil;
import com.pp.utils.constant.CacheKeyPrefixConstants;
import com.pp.utils.constant.SystemConstants;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Dominick Li
 * @description toekn认证
 **/
@Component
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String tokenKey = request.getHeader(SystemConstants.TOKEN_NAME);
        if (tokenKey != null) {
            Claims claims = JwtTokenUtil.getTokenClaim(tokenKey);
            if (claims != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = null;
                userDetails = new MyUserDetail();
//
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                logger.debug("authorication user: " + claims.getSubject() + ", setting security context");
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }
}
