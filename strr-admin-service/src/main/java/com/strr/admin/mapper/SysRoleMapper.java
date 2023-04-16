package com.strr.admin.mapper;

import com.strr.admin.model.SysRole;
import com.strr.base.mapper.SCrudMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysRoleMapper extends SCrudMapper<SysRole, Integer> {
}
