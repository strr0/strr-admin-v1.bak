package com.strr.base.controller;

import com.strr.base.model.Page;
import com.strr.base.model.Pageable;
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
    public int save(T entity) {
        return getService().save(entity);
    }

    @PutMapping("/update")
    public int update(T entity) {
        return getService().update(entity);
    }

    @DeleteMapping("/remove")
    public int remove(ID id) {
        return getService().remove(id);
    }

    @GetMapping("/get")
    public T get(ID id) {
        return getService().get(id);
    }
}
