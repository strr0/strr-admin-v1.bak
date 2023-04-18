package com.strr.base.controller;

import com.strr.base.model.Page;
import com.strr.base.model.Pageable;
import com.strr.base.model.Result;
import com.strr.base.service.SCrudService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.io.Serializable;

public abstract class SCrudController<T, ID extends Serializable> {
    protected abstract SCrudService<T, ID> getService();

    @GetMapping("/page")
    public Page<T> page(T param, Pageable pageable) {
        return getService().page(param, pageable);
    }

    @PostMapping("/save")
    public Result<T> save(T entity) {
        int r = getService().save(entity);
        if (r > 0) {
            return Result.ok(entity);
        }
        return Result.error();
    }

    @PutMapping("/update")
    public Result<T> update(T entity) {
        int r = getService().update(entity);
        if (r > 0) {
            return Result.ok(entity);
        }
        return Result.error();
    }

    @DeleteMapping("/remove")
    public Result<Void> remove(ID id) {
        int r = getService().remove(id);
        if (r > 0) {
            return Result.ok();
        }
        return Result.error();
    }

    @GetMapping("/get")
    public Result<T> get(ID id) {
        T t = getService().get(id);
        if (t != null) {
            return Result.ok(t);
        }
        return Result.error();
    }
}
