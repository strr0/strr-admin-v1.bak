package com.strr.admin.service.impl;

import com.strr.admin.mapper.SysUserMapper;
import com.strr.admin.model.SysUser;
import com.strr.admin.service.SysUserService;
import com.strr.base.mapper.SCrudMapper;
import com.strr.base.service.impl.SCrudServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysUserServiceImpl extends SCrudServiceImpl<SysUser, Integer> implements SysUserService {
    private final SysUserMapper sysUserMapper;

    @Autowired
    public SysUserServiceImpl(SysUserMapper sysUserMapper) {
        this.sysUserMapper = sysUserMapper;
    }

    @Override
    protected SCrudMapper<SysUser, Integer> getMapper() {
        return sysUserMapper;
    }
}
