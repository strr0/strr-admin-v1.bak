package com.strr.admin.controller;

import com.strr.admin.model.SysAuthority;
import com.strr.admin.model.SysAuthorityVO;
import com.strr.admin.model.SysUserDetails;
import com.strr.admin.service.SysAuthorityService;
import com.strr.admin.util.SysUtil;
import com.strr.base.controller.SCrudController;
import com.strr.base.model.Result;
import com.strr.base.service.SCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/sysAuthority")
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

    /**
     * 菜单树
     * @param param
     * @return
     */
    @GetMapping("/menuTree")
    public Result<List<SysAuthorityVO>> menuTree(SysAuthority param) {
        List<SysAuthority> sysAuthorityList = sysAuthorityService.listByParam(param);
        return Result.ok(SysUtil.buildMenuTree(sysAuthorityList));
    }

    /**
     * 用户菜单树
     * @return
     */
    @GetMapping("/userMenuTree")
    public Result<Map<String, Object>> userMenuTree(@AuthenticationPrincipal SysUserDetails sysUserDetails) {
        Map<String, Object> map = new HashMap<>();
        map.put("user", sysUserDetails);
        map.put("menus", SysUtil.buildMenuTree(sysUserDetails.getAuthorityList()));
        return Result.ok(map);
    }

    /**
     * 删除权限
     * @param id
     * @return
     */
    @DeleteMapping("/removeInfo")
    public Result<Void> removeInfo(Integer id) {
        sysAuthorityService.removeWithRel(id);
        return Result.ok();
    }
}
