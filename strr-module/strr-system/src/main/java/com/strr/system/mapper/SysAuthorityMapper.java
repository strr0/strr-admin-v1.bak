package com.strr.system.mapper;

import com.strr.base.mapper.CrudMapper;
import com.strr.system.model.SysAuthority;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysAuthorityMapper extends CrudMapper<SysAuthority, Integer> {
    /**
     * 获取用户权限
     * @param userId
     * @return
     */
    List<SysAuthority> listByUserId(Integer userId);

    /**
     * 删除角色权限关联
     * @param aid
     * @return
     */
    int removeRelByAid(Integer aid);
}
