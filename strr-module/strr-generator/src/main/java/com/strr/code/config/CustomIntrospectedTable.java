package com.strr.code.config;

import com.strr.code.controller.JavaBaseControllerGenerator;
import com.strr.code.mapper.JavaBaseMapperGenerator;
import com.strr.code.mapper.XmlBaseMapperGenerator;
import com.strr.code.model.JavaBaseRecordGenerator;
import com.strr.code.service.JavaBaseServiceGenerator;
import com.strr.code.util.VelocityUtil;
import org.mybatis.generator.api.ProgressCallback;
import org.mybatis.generator.codegen.AbstractJavaGenerator;
import org.mybatis.generator.codegen.mybatis3.IntrospectedTableMyBatis3Impl;
import org.mybatis.generator.config.ModelType;
import org.mybatis.generator.internal.rules.ConditionalModelRules;
import org.mybatis.generator.internal.rules.FlatModelRules;
import org.mybatis.generator.internal.rules.HierarchicalModelRules;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.List;

public class CustomIntrospectedTable extends IntrospectedTableMyBatis3Impl {
    private String basicName;

    private String moduleName;

    private String myBatis3JavaServiceType;

    private String myBatis3JavaServiceImplType;

    private String myBatis3JavaControllerType;

    public String getBasicName() {
        return basicName;
    }

    public void setBasicName(String basicName) {
        this.basicName = basicName;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getMyBatis3JavaServiceType() {
        return myBatis3JavaServiceType;
    }

    public void setMyBatis3JavaServiceType(String myBatis3JavaServiceType) {
        this.myBatis3JavaServiceType = myBatis3JavaServiceType;
    }

    public String getMyBatis3JavaServiceImplType() {
        return myBatis3JavaServiceImplType;
    }

    public void setMyBatis3JavaServiceImplType(String myBatis3JavaServiceImplType) {
        this.myBatis3JavaServiceImplType = myBatis3JavaServiceImplType;
    }

    public String getMyBatis3JavaControllerType() {
        return myBatis3JavaControllerType;
    }

    public void setMyBatis3JavaControllerType(String myBatis3JavaControllerType) {
        this.myBatis3JavaControllerType = myBatis3JavaControllerType;
    }

    // 初始化
    @Override
    public void initialize() {
        this.calculateModelAttributes();
        this.calculateXmlAttributes();
        this.calculateJavaMapperAttributes();
        this.calculateServiceAttributes();
        this.calculateControllerAttributes();
        if (this.tableConfiguration.getModelType() == ModelType.HIERARCHICAL) {
            this.rules = new HierarchicalModelRules(this);
        } else if (this.tableConfiguration.getModelType() == ModelType.FLAT) {
            this.rules = new FlatModelRules(this);
        } else {
            this.rules = new ConditionalModelRules(this);
        }
        this.context.getPlugins().initialized(this);
        // vue
        this.calculateVueAttributes();
    }

    // 设置model属性
    @Override
    protected void calculateModelAttributes() {
        String domainObjectName = this.fullyQualifiedTable.getDomainObjectName();
        this.setBasicName(domainObjectName.substring(0, 1).toLowerCase() + domainObjectName.substring(1));
        String pakkage = ((CustomContext)this.context).getTargetPackage();
        this.setBaseRecordType(String.format("%s.model.%s", pakkage, this.fullyQualifiedTable.getDomainObjectName()));
        int idx = pakkage.lastIndexOf(".");
        if (idx > 0) {
            this.setModuleName(pakkage.substring(idx));
        } else {
            this.setModuleName(pakkage);
        }
    }

    @Override
    protected void calculateXmlAttributes() {
        this.setMyBatis3XmlMapperFileName(this.calculateMyBatis3XmlMapperFileName());
        this.setMyBatis3XmlMapperPackage(this.getModuleName() + ".mapper");
        this.setMyBatis3FallbackSqlMapNamespace(this.calculateMyBatis3FallbackSqlMapNamespace());
        this.setSqlMapFullyQualifiedRuntimeTableName(this.calculateSqlMapFullyQualifiedRuntimeTableName());
        this.setSqlMapAliasedFullyQualifiedRuntimeTableName(this.calculateSqlMapAliasedFullyQualifiedRuntimeTableName());
    }

    // 设置mapper属性
    protected void calculateJavaMapperAttributes() {
        // mapper
        StringBuilder sb = new StringBuilder();
        sb.append(((CustomContext) this.context).getTargetPackage());
        sb.append(".mapper");
        sb.append(this.fullyQualifiedTable.getSubPackageForClientOrSqlMap(true));
        sb.append('.');
        if (StringUtility.stringHasValue(this.fullyQualifiedTable.getDomainObjectSubPackage())) {
            sb.append(this.fullyQualifiedTable.getDomainObjectSubPackage());
            sb.append('.');
        }
        sb.append(this.fullyQualifiedTable.getDomainObjectName());
        sb.append("Mapper");
        this.setMyBatis3JavaMapperType(sb.toString());
    }

    // 设置service属性
    protected void calculateServiceAttributes() {
        // service
        StringBuilder sb = new StringBuilder();
        sb.append(((CustomContext)this.context).getTargetPackage());
        sb.append(".service");
        sb.append(this.fullyQualifiedTable.getSubPackageForClientOrSqlMap(true));
        sb.append('.');
        if (StringUtility.stringHasValue(this.fullyQualifiedTable.getDomainObjectSubPackage())) {
            sb.append(this.fullyQualifiedTable.getDomainObjectSubPackage());
            sb.append('.');
        }
        sb.append(this.fullyQualifiedTable.getDomainObjectName());
        sb.append("Service");
        this.setMyBatis3JavaServiceType(sb.toString());
        // serviceImpl
        sb.setLength(0);
        sb.append(((CustomContext)this.context).getTargetPackage());
        sb.append(".service");
        sb.append(this.fullyQualifiedTable.getSubPackageForClientOrSqlMap(true));
        sb.append('.');
        if (StringUtility.stringHasValue(this.fullyQualifiedTable.getDomainObjectSubPackage())) {
            sb.append(this.fullyQualifiedTable.getDomainObjectSubPackage());
            sb.append('.');
        }
        sb.append("impl");
        sb.append('.');
        sb.append(this.fullyQualifiedTable.getDomainObjectName());
        sb.append("ServiceImpl");
        this.setMyBatis3JavaServiceImplType(sb.toString());
    }

    // 设置controller
    protected void calculateControllerAttributes() {
        // controller
        StringBuilder sb = new StringBuilder();
        sb.append(((CustomContext)this.context).getTargetPackage());
        sb.append(".controller");
        sb.append(this.fullyQualifiedTable.getSubPackageForClientOrSqlMap(true));
        sb.append('.');
        if (StringUtility.stringHasValue(this.fullyQualifiedTable.getDomainObjectSubPackage())) {
            sb.append(this.fullyQualifiedTable.getDomainObjectSubPackage());
            sb.append('.');
        }
        sb.append(this.fullyQualifiedTable.getDomainObjectName());
        sb.append("Controller");
        this.setMyBatis3JavaControllerType(sb.toString());
    }

    // vue
    protected void calculateVueAttributes() {
        VelocityUtil.setPath(moduleName + '/' + basicName + "/index.vue");
        VelocityUtil.loadContext(this);
    }

    // 生成
    @Override
    public void calculateGenerators(List<String> warnings, ProgressCallback progressCallback) {
        // java
        this.calculateJavaModelGenerators(warnings, progressCallback);
        this.calculateJavaMapperGenerators(warnings, progressCallback);
        this.calculateJavaServiceGenerators(warnings, progressCallback);
        this.calculateJavaControllerGenerators(warnings, progressCallback);
        // xml
        this.calculateXmlMapperGenerator(warnings, progressCallback);
    }

    // model
    @Override
    protected void calculateJavaModelGenerators(List<String> warnings, ProgressCallback progressCallback) {
        AbstractJavaGenerator javaGenerator = new JavaBaseRecordGenerator(((CustomContext)this.context).getTargetProject());
        this.initializeAbstractGenerator(javaGenerator, warnings, progressCallback);
        this.javaGenerators.add(javaGenerator);
    }

    // mapper
    protected void calculateJavaMapperGenerators(List<String> warnings, ProgressCallback progressCallback) {
        AbstractJavaGenerator javaGenerator = new JavaBaseMapperGenerator(((CustomContext)this.context).getTargetProject());
        this.initializeAbstractGenerator(javaGenerator, warnings, progressCallback);
        this.javaGenerators.add(javaGenerator);
    }

    // service
    protected void calculateJavaServiceGenerators(List<String> warnings, ProgressCallback progressCallback) {
        AbstractJavaGenerator javaGenerator = new JavaBaseServiceGenerator(((CustomContext)this.context).getTargetProject());
        this.initializeAbstractGenerator(javaGenerator, warnings, progressCallback);
        this.javaGenerators.add(javaGenerator);
    }

    // controller
    protected void calculateJavaControllerGenerators(List<String> warnings, ProgressCallback progressCallback) {
        AbstractJavaGenerator javaGenerator = new JavaBaseControllerGenerator(((CustomContext)this.context).getTargetProject());
        this.initializeAbstractGenerator(javaGenerator, warnings, progressCallback);
        this.javaGenerators.add(javaGenerator);
    }

    // xmlmapper
    protected void calculateXmlMapperGenerator(List<String> warnings, ProgressCallback progressCallback) {
        this.xmlMapperGenerator = new XmlBaseMapperGenerator();
        this.initializeAbstractGenerator(this.xmlMapperGenerator, warnings, progressCallback);
    }
}
