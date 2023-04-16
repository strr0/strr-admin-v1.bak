package com.strr.base.service.impl;

import com.strr.base.mapper.SCrudMapper;
import com.strr.base.model.Page;
import com.strr.base.model.Pageable;
import com.strr.base.service.SCrudService;
import com.strr.util.PageUtil;

import java.io.Serializable;

public abstract class SCrudServiceImpl<T, ID extends Serializable> implements SCrudService<T, ID> {
    protected abstract SCrudMapper<T, ID> getMapper();

    @Override
    public Page<T> page(T param, Pageable pageable) {
        Page<T> page = pageable.toPage();
        int count = getMapper().countByParam(param);
        page.setTotal(count);
        if (count > 0) {
            PageUtil.start(pageable);
            page.setContent(getMapper().listByParam(param));
            PageUtil.end();
        }
        return page;
    }

    @Override
    public int save(T entity) {
        return getMapper().save(entity);
    }

    @Override
    public int update(T entity) {
        return getMapper().update(entity);
    }

    @Override
    public int remove(ID id) {
        return getMapper().remove(id);
    }

    @Override
    public T get(ID id) {
        return getMapper().get(id);
    }
}
