package com.strr.admin.service;

import com.strr.admin.model.SysAuthority;
import com.strr.base.service.SCrudService;

import java.util.List;

public interface SysAuthorityService extends SCrudService<SysAuthority, Integer> {
    /**
     * 权限列表
     * @param param
     * @return
     */
    List<SysAuthority> listByParam(SysAuthority param);

    /**
     * 获取用户权限
     * @param userId
     * @return
     */
    List<SysAuthority> listByUserId(Integer userId);

    /**
     * 删除权限
     * @param id
     */
    void removeWithRel(Integer id);
}
