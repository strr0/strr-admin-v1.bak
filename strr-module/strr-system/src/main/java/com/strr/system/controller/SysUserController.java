package com.strr.system.controller;

import com.strr.base.controller.SCrudController;
import com.strr.base.model.Result;
import com.strr.base.service.CrudService;
import com.strr.system.model.SysUser;
import com.strr.system.service.ISysUserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/sysUser")
public class SysUserController extends SCrudController<SysUser, Integer> {
    private final ISysUserService sysUserService;

    public SysUserController(ISysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    @Override
    protected CrudService<SysUser, Integer> getService() {
        return sysUserService;
    }

    /**
     * 保存用户信息
     * @param sysUser
     * @param oldRids
     * @param newRids
     * @return
     */
    @PostMapping("/saveInfo")
    public Result<Void> saveInfo(SysUser sysUser, Integer[] oldRids, Integer[] newRids) {
        sysUserService.saveWithRel(sysUser, oldRids, newRids);
        return Result.ok();
    }

    /**
     * 获取用户角色
     * @param uid
     * @return
     */
    @GetMapping("/listRelByUid")
    public Result<List<Integer>> listRelByUid(Integer uid) {
        List<Integer> data = sysUserService.listRelByUid(uid);
        return Result.ok(data);
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    @DeleteMapping("/removeInfo")
    public Result<Void> removeInfo(Integer id) {
        sysUserService.removeWithRel(id);
        return Result.ok();
    }
}
