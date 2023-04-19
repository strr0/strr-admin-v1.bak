package com.strr.code.controller;

import com.strr.code.config.CustomIntrospectedTable;
import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.codegen.AbstractJavaGenerator;
import org.mybatis.generator.internal.util.messages.Messages;

import java.util.Collections;
import java.util.List;

public class JavaBaseControllerGenerator extends AbstractJavaGenerator {
    public JavaBaseControllerGenerator(String project) {
        super(project);
    }

    @Override
    public List<CompilationUnit> getCompilationUnits() {
        this.progressCallback.startTask(Messages.getString("Progress.controller", this.introspectedTable.getFullyQualifiedTable().toString()));
        CommentGenerator commentGenerator = this.context.getCommentGenerator();
        FullyQualifiedJavaType type = new FullyQualifiedJavaType(((CustomIntrospectedTable)this.introspectedTable).getMyBatis3JavaControllerType());
        TopLevelClass topLevelClass = new TopLevelClass(type);
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);
        commentGenerator.addJavaFileComment(topLevelClass);
        this.addBasicInfo(topLevelClass);
        this.addBasicConstructor(topLevelClass);
        this.addBasicClass(topLevelClass);
        return Collections.singletonList(topLevelClass);
    }

    // 基本信息
    protected void addBasicInfo(TopLevelClass topLevelClass) {
        String basicName = ((CustomIntrospectedTable)this.introspectedTable).getBasicName();
        String moduleName = ((CustomIntrospectedTable) this.introspectedTable).getModuleName();
        topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.web.bind.annotation.RestController"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.web.bind.annotation.RequestMapping"));
        topLevelClass.addAnnotation("@RestController");
        topLevelClass.addAnnotation(String.format("@RequestMapping(\"/api/%s/%s\")", moduleName, basicName));
    }

    // 构造函数
    protected void addBasicConstructor(TopLevelClass topLevelClass) {
        String basicName = ((CustomIntrospectedTable)this.introspectedTable).getBasicName();
        // service
        FullyQualifiedJavaType service = new FullyQualifiedJavaType(((CustomIntrospectedTable)this.introspectedTable).getMyBatis3JavaServiceType());
        topLevelClass.addImportedType(service);
        Field field = new Field(basicName + "Service", service);
        field.setVisibility(JavaVisibility.PRIVATE);
        field.setFinal(true);
        topLevelClass.addField(field);
        // 构造函数
        topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.beans.factory.annotation.Autowired"));
        Method method = new Method(topLevelClass.getType().getShortName());
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setConstructor(true);
        method.addParameter(new Parameter(service, basicName + "Service"));
        method.addBodyLine(String.format("this.%sService = %sService;", basicName, basicName));
        method.addAnnotation("@Autowired");
        this.context.getCommentGenerator().addGeneralMethodComment(method, this.introspectedTable);
        topLevelClass.addMethod(method);
    }

    // 基类信息
    protected void addBasicClass(TopLevelClass topLevelClass) {
        List<IntrospectedColumn> introspectedColumns = this.introspectedTable.getPrimaryKeyColumns();
        if (introspectedColumns.isEmpty()) {
            return;
        }
        String basicName = ((CustomIntrospectedTable)this.introspectedTable).getBasicName();
        FullyQualifiedJavaType entity = new FullyQualifiedJavaType(this.introspectedTable.getBaseRecordType());
        topLevelClass.addImportedType(entity);
        IntrospectedColumn introspectedColumn = introspectedColumns.get(0);
        FullyQualifiedJavaType key = introspectedColumn.getFullyQualifiedJavaType();
        topLevelClass.addImportedType(key);
        // 基类
        FullyQualifiedJavaType baseController = new FullyQualifiedJavaType("com.strr.base.controller.SCrudController");
        baseController.addTypeArgument(entity);
        baseController.addTypeArgument(key);
        topLevelClass.setSuperClass(baseController);
        topLevelClass.addImportedType(baseController);
        // 基类方法
        FullyQualifiedJavaType baseService = new FullyQualifiedJavaType("com.strr.base.service.SCrudService");
        baseService.addTypeArgument(entity);
        baseService.addTypeArgument(key);
        topLevelClass.addImportedType(baseService);
        Method method = new Method("getService");
        method.setVisibility(JavaVisibility.PROTECTED);
        method.setReturnType(baseService);
        method.addBodyLine(String.format("return %sService;", basicName));
        method.addAnnotation("@Override");
        topLevelClass.addMethod(method);
    }
}
