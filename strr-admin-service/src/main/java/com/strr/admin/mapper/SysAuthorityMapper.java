package com.strr.admin.mapper;

import com.strr.admin.model.SysAuthority;
import com.strr.base.mapper.SCrudMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysAuthorityMapper extends SCrudMapper<SysAuthority, Integer> {
    List<SysAuthority> list();
}
