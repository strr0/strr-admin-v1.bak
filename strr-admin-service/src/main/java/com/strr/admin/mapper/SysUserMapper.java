package com.strr.admin.mapper;

import com.strr.admin.model.SysUser;
import com.strr.admin.model.SysUserDetails;
import com.strr.base.mapper.SCrudMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysUserMapper extends SCrudMapper<SysUser, Integer> {
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
    int deleteRelByUid(Integer uid);

    /**
     * 获取用户
     * @param username
     * @return
     */
    List<SysUserDetails> getByUsername(String username);
}
