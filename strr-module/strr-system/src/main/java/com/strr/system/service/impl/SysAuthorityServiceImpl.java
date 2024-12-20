package com.strr.system.service.impl;

import com.strr.base.mapper.CrudMapper;
import com.strr.base.service.impl.CrudServiceImpl;
import com.strr.system.mapper.SysAuthorityMapper;
import com.strr.system.model.SysAuthority;
import com.strr.system.service.ISysAuthorityService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysAuthorityServiceImpl extends CrudServiceImpl<SysAuthority, Integer> implements ISysAuthorityService {
    private final SysAuthorityMapper sysAuthorityMapper;

    public SysAuthorityServiceImpl(SysAuthorityMapper sysAuthorityMapper) {
        this.sysAuthorityMapper = sysAuthorityMapper;
    }

    @Override
    protected CrudMapper<SysAuthority, Integer> getMapper() {
        return sysAuthorityMapper;
    }

    /**
     * 权限列表
     * @param param
     * @return
     */
    @Override
    public List<SysAuthority> listByParam(SysAuthority param) {
        return sysAuthorityMapper.listByParam(param);
    }

    /**
     * 获取用户权限
     * @param userId
     * @return
     */
    @Override
    public List<SysAuthority> listByUserId(Integer userId) {
        return sysAuthorityMapper.listByUserId(userId);
    }

    /**
     * 删除权限
     * @param id
     */
    @Override
    public void removeWithRel(Integer id) {
        sysAuthorityMapper.remove(id);
        sysAuthorityMapper.removeRelByAid(id);
    }
}
