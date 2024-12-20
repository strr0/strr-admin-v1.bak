package com.strr.code.config;

public class CustomConfiguration {
    private String url;
    private String driver;
    private String user;
    private String password;

    private String targetPackage;
    private String targetProject;
    private String clientProject;
    private String vueProject;

    private String[] tables;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTargetPackage() {
        return targetPackage;
    }

    public void setTargetPackage(String targetPackage) {
        this.targetPackage = targetPackage;
    }

    public String getTargetProject() {
        return targetProject;
    }

    public void setTargetProject(String targetProject) {
        this.targetProject = targetProject;
    }

    public String getClientProject() {
        return clientProject;
    }

    public void setClientProject(String clientProject) {
        this.clientProject = clientProject;
    }

    public String getVueProject() {
        return vueProject;
    }

    public void setVueProject(String vueProject) {
        this.vueProject = vueProject;
    }

    public String[] getTables() {
        return tables;
    }

    public void setTables(String[] tables) {
        this.tables = tables;
    }
}
