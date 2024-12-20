package com.strr.system.util;


import com.strr.system.model.SysAuthority;
import com.strr.system.model.SysAuthorityVO;

import java.util.*;

public class MenuUtil {
    /**
     * 菜单树
     * @param authorities
     * @return
     */
    public static List<SysAuthorityVO> buildMenuTree(List<SysAuthority> authorities) {
        List<SysAuthorityVO> tree = new ArrayList<>();
        Map<Integer, SysAuthorityVO> map = new HashMap<>();
        authorities.forEach(authority -> {
            SysAuthorityVO node = authorityToVO(authority);
            SysAuthorityVO parent = map.get(authority.getParentId());
            if (parent != null) {
                // 子菜单
                List<SysAuthorityVO> children = parent.getChildren();
                if (children == null) {
                    children = new ArrayList<>();
                    parent.setChildren(children);
                }
                node.setParentName(parent.getName());
                children.add(node);
            } else {
                tree.add(node);
            }
            map.put(node.getId(), node);
        });
        return tree;
    }

    /**
     * 权限转VO
     * @param authority
     * @return
     */
    private static SysAuthorityVO authorityToVO(SysAuthority authority) {
        SysAuthorityVO authorityVO = new SysAuthorityVO();
        authorityVO.setId(authority.getId());
        authorityVO.setUrl(authority.getUrl());
        authorityVO.setPath(authority.getPath());
        authorityVO.setName(authority.getName());
        authorityVO.setIcon(authority.getIcon());
        authorityVO.setParentId(authority.getParentId());
        authorityVO.setIsMenu(authority.getIsMenu());
        authorityVO.setType(authority.getType());
        authorityVO.setFunc(authority.getFunc());
        return authorityVO;
    }
}
