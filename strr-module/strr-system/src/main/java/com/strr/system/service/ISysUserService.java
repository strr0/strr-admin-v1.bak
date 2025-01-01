package com.strr.system.service;

import com.strr.base.service.CrudService;
import com.strr.system.model.SysUser;
import com.strr.system.model.SysUserDetails;

import java.util.List;

public interface ISysUserService extends CrudService<SysUser, Integer> {
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

    /**
     * 获取用户
     * @param username
     * @return
     */
    SysUserDetails getByUsername(String username);
}
