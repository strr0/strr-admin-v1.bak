package com.strr.system.mapper;

import com.strr.base.mapper.CrudMapper;
import com.strr.system.model.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysRoleMapper extends CrudMapper<SysRole, Integer> {
    /**
     * 获取用户角色
     * @param userId
     * @return
     */
    List<SysRole> listByUserId(Integer userId);

    /**
     * 添加角色权限
     * @param rid
     * @param aid
     * @return
     */
    int saveRel(@Param("rid") Integer rid, @Param("aid") Integer aid);

    /**
     * 删除角色权限
     * @param rid
     * @param aid
     * @return
     */
    int removeRel(@Param("rid") Integer rid, @Param("aid") Integer aid);

    /**
     * 获取权限id
     * @param rid
     * @return
     */
    List<Integer> listRelByRid(Integer rid);

    /**
     * 删除角色权限关联
     * @param rid
     * @return
     */
    int removeRARelByRid(Integer rid);

    /**
     * 删除用户角色关联
     * @param rid
     * @return
     */
    int removeURRelByRid(Integer rid);
}
