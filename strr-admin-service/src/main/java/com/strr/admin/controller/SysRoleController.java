package com.strr.admin.controller;

import com.strr.admin.model.SysRole;
import com.strr.admin.service.SysRoleService;
import com.strr.base.controller.SCrudController;
import com.strr.base.service.SCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends SCrudController<SysRole, Integer> {
    private final SysRoleService sysRoleService;

    @Autowired
    public SysRoleController(SysRoleService sysRoleService) {
        this.sysRoleService = sysRoleService;
    }

    @Override
    protected SCrudService<SysRole, Integer> getService() {
        return sysRoleService;
    }
}
