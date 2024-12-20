package com.strr.system.service.impl;

import com.strr.system.mapper.SysAuthorityMapper;
import com.strr.system.mapper.SysRoleMapper;
import com.strr.system.mapper.SysUserMapper;
import com.strr.system.model.SysUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final SysUserMapper sysUserMapper;
    private final SysRoleMapper sysRoleMapper;
    private final SysAuthorityMapper sysAuthorityMapper;

    public UserDetailsServiceImpl(SysUserMapper sysUserMapper, SysRoleMapper sysRoleMapper, SysAuthorityMapper sysAuthorityMapper) {
        this.sysUserMapper = sysUserMapper;
        this.sysRoleMapper = sysRoleMapper;
        this.sysAuthorityMapper = sysAuthorityMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<SysUserDetails> userDetailsList = sysUserMapper.getByUsername(username);
        if (!userDetailsList.isEmpty()) {
            SysUserDetails userDetails = userDetailsList.get(0);
            userDetails.setRoleList(sysRoleMapper.listByUserId(userDetails.getId()));
            userDetails.setAuthorityList(sysAuthorityMapper.listByUserId(userDetails.getId()));
            return userDetails;
        }
        throw new UsernameNotFoundException(String.format("User '%s' not found.", username));
    }
}
