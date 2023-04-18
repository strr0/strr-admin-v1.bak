package com.strr.admin.service;

import com.strr.admin.model.SysRole;
import com.strr.base.service.SCrudService;

import java.util.List;

public interface SysRoleService extends SCrudService<SysRole, Integer> {
    /**
     * 获取角色列表
     * @return
     */
    List<SysRole> list();

    /**
     * 更新角色权限
     * @param rid
     * @param oldAids
     * @param newAids
     * @return
     */
    void updateRel(Integer rid, Integer[] oldAids, Integer[] newAids);

    /**
     * 获取角色权限
     * @param rid
     * @return
     */
    List<Integer> listRelByRid(Integer rid);

    /**
     * 删除角色
     * @param id
     */
    void removeWithRel(Integer id);
}
