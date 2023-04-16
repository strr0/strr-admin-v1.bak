package com.strr.admin.model;

import com.strr.base.annotation.SColumn;
import com.strr.base.annotation.SId;
import com.strr.base.annotation.STable;

import java.util.Date;

@STable("sys_authority")
public class SysAuthority {
    /**
     * 编号
     */
    @SId
    private Integer id;

    /**
     * 请求路径
     */
    private String url;

    /**
     * 路由
     */
    private String path;

    /**
     * 名称
     */
    private String name;

    /**
     * 图标
     */
    private String icon;

    /**
     * 父菜单
     */
    @SColumn("parent_id")
    private Integer parentId;

    /**
     * 是否为菜单
     */
    @SColumn("is_menu")
    private Boolean isMenu;

    /**
     * 按钮类型
     */
    private String type;

    /**
     * 方法
     */
    private String func;

    /**
     * 创建人
     */
    private Integer creator;

    /**
     * 创建时间
     */
    @SColumn("create_time")
    private Date createTime;

    /**
     * 更新人
     */
    private Integer updator;

    /**
     * 更新时间
     */
    @SColumn("update_time")
    private Date updateTime;

    /**
     * 状态
     */
    private Boolean status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Boolean getIsMenu() {
        return isMenu;
    }

    public void setIsMenu(Boolean isMenu) {
        this.isMenu = isMenu;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFunc() {
        return func;
    }

    public void setFunc(String func) {
        this.func = func;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getUpdator() {
        return updator;
    }

    public void setUpdator(Integer updator) {
        this.updator = updator;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
