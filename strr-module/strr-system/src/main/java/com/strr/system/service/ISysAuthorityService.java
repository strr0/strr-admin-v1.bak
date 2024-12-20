package com.strr.system.service;

import com.strr.base.service.CrudService;
import com.strr.system.model.SysAuthority;

import java.util.List;

public interface ISysAuthorityService extends CrudService<SysAuthority, Integer> {
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
