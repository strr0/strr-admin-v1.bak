package com.strr.system.service.impl;

import com.strr.base.mapper.CrudMapper;
import com.strr.base.service.impl.CrudServiceImpl;
import com.strr.system.mapper.SysRoleMapper;
import com.strr.system.model.SysRole;
import com.strr.system.service.ISysRoleService;
import com.strr.system.util.ListUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysRoleServiceImpl extends CrudServiceImpl<SysRole, Integer> implements ISysRoleService {
    private final SysRoleMapper sysRoleMapper;

    public SysRoleServiceImpl(SysRoleMapper sysRoleMapper) {
        this.sysRoleMapper = sysRoleMapper;
    }

    @Override
    protected CrudMapper<SysRole, Integer> getMapper() {
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
        for (Integer aid : ListUtil.subtraction(oldAids, newAids)) {
            sysRoleMapper.removeRel(rid, aid);
        }
        for (Integer aid : ListUtil.subtraction(newAids, oldAids)) {
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
