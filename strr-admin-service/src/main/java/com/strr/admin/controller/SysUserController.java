package com.strr.admin.controller;

import com.strr.admin.model.SysUser;
import com.strr.admin.service.SysUserService;
import com.strr.base.controller.SCrudController;
import com.strr.base.service.SCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sys/user")
public class SysUserController extends SCrudController<SysUser, Integer> {
    private final SysUserService sysUserService;

    @Autowired
    public SysUserController(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    @Override
    protected SCrudService<SysUser, Integer> getService() {
        return sysUserService;
    }
}
