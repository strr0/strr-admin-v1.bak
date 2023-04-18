package com.strr.admin.service;

import com.strr.admin.model.SysUser;
import com.strr.base.service.SCrudService;

import java.util.List;

public interface SysUserService extends SCrudService<SysUser, Integer> {
    /**
     * 保存用户
     * @param sysUser
     * @param oldRids
     * @param newRids
     */
    void saveWithRel(SysUser sysUser, Integer[] oldRids, Integer[] newRids);

    /**
     * 获取用户角色
     * @param uid
     * @return
     */
    List<Integer> listRelByUid(Integer uid);

    /**
     * 删除用户
     * @param id
     */
    void removeWithRel(Integer id);
}
