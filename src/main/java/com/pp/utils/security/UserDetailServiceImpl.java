package com.pp.utils.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Dominick Li
 * @description 空实现
 **/
@Service
public class UserDetailServiceImpl implements org.springframework.security.core.userdetails.UserDetailsService{
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
