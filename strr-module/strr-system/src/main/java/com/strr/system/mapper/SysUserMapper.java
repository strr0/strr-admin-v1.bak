package com.strr.system.mapper;

import com.strr.base.mapper.CrudMapper;
import com.strr.system.model.SysUser;
import com.strr.system.model.SysUserDetails;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysUserMapper extends CrudMapper<SysUser, Integer> {
    /**
     * 添加用户角色
     * @param uid
     * @param rid
     * @return
     */
    int saveRel(@Param("uid") Integer uid, @Param("rid") Integer rid);

    /**
     * 删除用户角色关联
     * @param uid
     * @param rid
     * @return
     */
    int removeRel(@Param("uid") Integer uid, @Param("rid") Integer rid);

    /**
     * 获取角色id
     * @param uid
     * @return
     */
    List<Integer> listRelByUid(Integer uid);

    /**
     * 删除用户角色关联
     * @param uid
     * @return
     */
    int removeRelByUid(Integer uid);

    /**
     * 获取用户
     * @param username
     * @return
     */
    List<SysUserDetails> getByUsername(String username);
}
