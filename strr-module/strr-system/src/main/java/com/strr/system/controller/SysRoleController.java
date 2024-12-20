package com.strr.system.controller;

import com.strr.base.controller.SCrudController;
import com.strr.base.model.Result;
import com.strr.base.service.CrudService;
import com.strr.system.model.SysRole;
import com.strr.system.service.ISysRoleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/sysRole")
public class SysRoleController extends SCrudController<SysRole, Integer> {
    private final ISysRoleService sysRoleService;

    public SysRoleController(ISysRoleService sysRoleService) {
        this.sysRoleService = sysRoleService;
    }

    @Override
    protected CrudService<SysRole, Integer> getService() {
        return sysRoleService;
    }

    /**
     * 获取角色列表
     * @return
     */
    @GetMapping("/list")
    public Result<List<SysRole>> list() {
        List<SysRole> list = sysRoleService.list();
        return Result.ok(list);
    }

    /**
     * 更新角色权限
     * @param rid
     * @param oldAids
     * @param newAids
     * @return
     */
    @PostMapping("/updateRel")
    public Result<Void> updateRel(Integer rid, Integer[] oldAids, Integer[] newAids) {
        sysRoleService.updateRel(rid, oldAids, newAids);
        return Result.ok();
    }

    /**
     * 获取角色权限
     * @param rid
     * @return
     */
    @GetMapping("/listRelByRid")
    public Result<List<Integer>> listRelByRid(Integer rid) {
        List<Integer> data = sysRoleService.listRelByRid(rid);
        return Result.ok(data);
    }

    /**
     * 删除角色
     * @param id
     * @return
     */
    @DeleteMapping("/removeInfo")
    public Result<Void> removeInfo(Integer id) {
        sysRoleService.removeWithRel(id);
        return Result.ok();
    }
}
