package com.strr.admin.service.impl;

import com.strr.admin.mapper.SysRoleMapper;
import com.strr.admin.model.SysRole;
import com.strr.admin.service.SysRoleService;
import com.strr.base.mapper.SCrudMapper;
import com.strr.base.service.impl.SCrudServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysRoleServiceImpl extends SCrudServiceImpl<SysRole, Integer> implements SysRoleService {
    private final SysRoleMapper sysRoleMapper;

    @Autowired
    public SysRoleServiceImpl(SysRoleMapper sysRoleMapper) {
        this.sysRoleMapper = sysRoleMapper;
    }

    @Override
    protected SCrudMapper<SysRole, Integer> getMapper() {
        return sysRoleMapper;
    }
}
