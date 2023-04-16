package com.strr.admin.service;

import com.strr.admin.model.SysAuthority;
import com.strr.base.service.SCrudService;

import java.util.List;

public interface SysAuthorityService extends SCrudService<SysAuthority, Integer> {
    List<SysAuthority> list();
}
