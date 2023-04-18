package com.strr.admin.service.impl;

import com.strr.admin.mapper.SysRoleMapper;
import com.strr.admin.model.SysRole;
import com.strr.admin.service.SysRoleService;
import com.strr.admin.util.SysUtil;
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

    /**
     * 获取角色列表
     * @return
     */
    @Override
    public List<SysRole> list() {
        return sysRoleMapper.listByParam(null);
    }

    /**
     * 更新角色权限
     * @param rid
     * @param oldAids
     * @param newAids
     */
    @Override
    public void updateRel(Integer rid, Integer[] oldAids, Integer[] newAids) {
        for (Integer aid : SysUtil.subtraction(oldAids, newAids)) {
            sysRoleMapper.removeRel(rid, aid);
        }
        for (Integer aid : SysUtil.subtraction(newAids, oldAids)) {
            sysRoleMapper.saveRel(rid, aid);
        }
    }

    /**
     * 获取角色权限
     * @param rid
     * @return
     */
    @Override
    public List<Integer> listRelByRid(Integer rid) {
        return sysRoleMapper.listRelByRid(rid);
    }

    /**
     * 删除角色
     * @param id
     */
    @Override
    public void removeWithRel(Integer id) {
        sysRoleMapper.remove(id);
        sysRoleMapper.removeRARelByRid(id);
        sysRoleMapper.removeURRelByRid(id);
    }
}
