package com.strr.code.config;

import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.ModelType;
import org.mybatis.generator.config.TableConfiguration;

public class CustomContext extends Context {
    private String targetPackage;
    private String targetProject;
    private String clientProject;

    public CustomContext(ModelType defaultModelType) {
        super(defaultModelType);
        super.setId("simple");
        super.setTargetRuntime("com.strr.code.config.CustomIntrospectedTable");
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

    // 添加表
    public void addTable(String tableName) {
        TableConfiguration table = new TableConfiguration(this);
        table.setTableName(tableName);
        super.addTableConfiguration(table);
    }
}
