package com.strr.system.service.impl;

import com.strr.base.mapper.CrudMapper;
import com.strr.base.service.impl.CrudServiceImpl;
import com.strr.system.mapper.SysUserMapper;
import com.strr.system.model.SysUser;
import com.strr.system.model.SysUserDetails;
import com.strr.system.service.ISysUserService;
import com.strr.system.util.ListUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class SysUserServiceImpl extends CrudServiceImpl<SysUser, Integer> implements ISysUserService {
    private final SysUserMapper sysUserMapper;

    public SysUserServiceImpl(SysUserMapper sysUserMapper) {
        this.sysUserMapper = sysUserMapper;
    }

    @Override
    protected CrudMapper<SysUser, Integer> getMapper() {
        return sysUserMapper;
    }

    /**
     * 保存用户
     * @param sysUser
     * @param oldRids
     * @param newRids
     */
    @Override
    public void saveWithRel(SysUser sysUser, Integer[] oldRids, Integer[] newRids) {
        if (sysUser.getId() == null) {
            if (sysUser.getPassword() == null) {
                sysUser.setPassword(new BCryptPasswordEncoder().encode("abc123"));
            }
            save(sysUser);
        } else {
            update(sysUser);
        }
        for (Integer rid : ListUtil.subtraction(oldRids, newRids)) {
            sysUserMapper.removeRel(sysUser.getId(), rid);
        }
        for (Integer rid : ListUtil.subtraction(newRids, oldRids)) {
            sysUserMapper.saveRel(sysUser.getId(), rid);
        }
    }

    /**
     * 获取用户角色
     * @param uid
     * @return
     */
    @Override
    public List<Integer> listRelByUid(Integer uid) {
        return sysUserMapper.listRelByUid(uid);
    }

    /**
     * 删除用户
     * @param id
     */
    @Override
    public void removeWithRel(Integer id) {
        sysUserMapper.remove(id);
        sysUserMapper.removeRelByUid(id);
    }

    /**
     * 获取用户
     * @param username
     * @return
     */
    @Override
    public SysUserDetails getByUsername(String username) {
        List<SysUserDetails> userDetails = sysUserMapper.getByUsername(username);
        if (!userDetails.isEmpty()) {
            SysUserDetails sysUserDetails = userDetails.get(0);
            sysUserDetails.setRoleList(Collections.emptyList());
            sysUserDetails.setAuthorityList(Collections.emptyList());
            return sysUserDetails;
        }
        return null;
    }
}
