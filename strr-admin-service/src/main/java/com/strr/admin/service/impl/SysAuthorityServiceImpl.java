package com.strr.admin.service.impl;

import com.strr.admin.mapper.SysAuthorityMapper;
import com.strr.admin.model.SysAuthority;
import com.strr.admin.service.SysAuthorityService;
import com.strr.base.mapper.SCrudMapper;
import com.strr.base.service.impl.SCrudServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysAuthorityServiceImpl extends SCrudServiceImpl<SysAuthority, Integer> implements SysAuthorityService {
    private final SysAuthorityMapper sysAuthorityMapper;

    @Autowired
    public SysAuthorityServiceImpl(SysAuthorityMapper sysAuthorityMapper) {
        this.sysAuthorityMapper = sysAuthorityMapper;
    }

    @Override
    protected SCrudMapper<SysAuthority, Integer> getMapper() {
        return sysAuthorityMapper;
    }

    @Override
    public List<SysAuthority> list() {
        return sysAuthorityMapper.list();
    }
}
