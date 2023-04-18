package com.strr.admin.mapper;

import com.strr.admin.model.SysAuthority;
import com.strr.base.mapper.SCrudMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysAuthorityMapper extends SCrudMapper<SysAuthority, Integer> {
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
