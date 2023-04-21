package com.strr.code.model;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.FullyQualifiedTable;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.Plugin;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.codegen.AbstractJavaGenerator;
import org.mybatis.generator.codegen.RootClassInfo;
import org.mybatis.generator.internal.util.JavaBeansUtil;
import org.mybatis.generator.internal.util.messages.Messages;

import java.util.Collections;
import java.util.List;

public class JavaBaseRecordGenerator extends AbstractJavaGenerator {
    public JavaBaseRecordGenerator(String project) {
        super(project);
    }

    @Override
    public List<CompilationUnit> getCompilationUnits() {
        FullyQualifiedTable table = this.introspectedTable.getFullyQualifiedTable();
        this.progressCallback.startTask(Messages.getString("Progress.record", table.toString()));
        CommentGenerator commentGenerator = this.context.getCommentGenerator();
        FullyQualifiedJavaType type = new FullyQualifiedJavaType(this.introspectedTable.getBaseRecordType());
        TopLevelClass topLevelClass = new TopLevelClass(type);
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);
        topLevelClass.addImportedType(new FullyQualifiedJavaType("com.strr.base.annotation.STable"));
        topLevelClass.addAnnotation(String.format("@STable(\"%s\")", table));
        commentGenerator.addJavaFileComment(topLevelClass);
        commentGenerator.addModelClassComment(topLevelClass, this.introspectedTable);
        this.addColumns(topLevelClass);
        return Collections.singletonList(topLevelClass);
    }

    protected void addColumns(TopLevelClass topLevelClass) {
        Plugin plugins = this.context.getPlugins();
        topLevelClass.addImportedType(new FullyQualifiedJavaType("com.strr.base.annotation.SId"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("com.strr.base.annotation.SColumn"));
        // 字段
        List<IntrospectedColumn> introspectedColumns = this.introspectedTable.getAllColumns();
        boolean existKey = !this.introspectedTable.getPrimaryKeyColumns().isEmpty();
        String rootClass = this.getRootClass();
        for (IntrospectedColumn introspectedColumn : introspectedColumns) {
            if (!RootClassInfo.getInstance(rootClass, this.warnings).containsProperty(introspectedColumn)) {
                Field field = JavaBeansUtil.getJavaBeansField(introspectedColumn, this.context, this.introspectedTable);
                // 是否主键
                if (existKey) {
                    // 主键字段默认排在第一个
                    field.addAnnotation("@SId");
                    existKey = false;
                }
                // 对应字段
                field.addAnnotation(String.format("@SColumn(\"%s\")", introspectedColumn.getActualColumnName()));
                if (plugins.modelFieldGenerated(field, topLevelClass, introspectedColumn, this.introspectedTable, Plugin.ModelClassType.BASE_RECORD)) {
                    topLevelClass.addField(field);
                    topLevelClass.addImportedType(field.getType());
                }
                // get方法
                Method method = JavaBeansUtil.getJavaBeansGetter(introspectedColumn, this.context, this.introspectedTable);
                if (plugins.modelGetterMethodGenerated(method, topLevelClass, introspectedColumn, this.introspectedTable, Plugin.ModelClassType.BASE_RECORD)) {
                    topLevelClass.addMethod(method);
                }
                // set方法
                method = JavaBeansUtil.getJavaBeansSetter(introspectedColumn, this.context, this.introspectedTable);
                if (plugins.modelSetterMethodGenerated(method, topLevelClass, introspectedColumn, this.introspectedTable, Plugin.ModelClassType.BASE_RECORD)) {
                    topLevelClass.addMethod(method);
                }
            }
        }
    }
}
