package com.strr.admin.controller;

import com.strr.admin.model.SysAuthority;
import com.strr.admin.service.SysAuthorityService;
import com.strr.base.controller.SCrudController;
import com.strr.base.service.SCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sys/authority")
public class SysAuthorityController extends SCrudController<SysAuthority, Integer> {
    private final SysAuthorityService sysAuthorityService;

    @Autowired
    public SysAuthorityController(SysAuthorityService sysAuthorityService) {
        this.sysAuthorityService = sysAuthorityService;
    }

    @Override
    protected SCrudService<SysAuthority, Integer> getService() {
        return sysAuthorityService;
    }

    @GetMapping("/list")
    public List<SysAuthority> list() {
        return sysAuthorityService.list();
    }
}
