package com.strr.code.service;

import com.strr.code.config.CustomIntrospectedTable;
import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.codegen.AbstractJavaGenerator;
import org.mybatis.generator.internal.util.messages.Messages;

import java.util.ArrayList;
import java.util.List;

public class JavaBaseServiceGenerator extends AbstractJavaGenerator {
    public JavaBaseServiceGenerator(String project) {
        super(project);
    }

    @Override
    public List<CompilationUnit> getCompilationUnits() {
        this.progressCallback.startTask(Messages.getString("Progress.service", this.introspectedTable.getFullyQualifiedTable().toString()));
        CommentGenerator commentGenerator = this.context.getCommentGenerator();
        FullyQualifiedJavaType type = new FullyQualifiedJavaType(((CustomIntrospectedTable)this.introspectedTable).getMyBatis3JavaServiceType());
        Interface interfaze = new Interface(type);
        interfaze.setVisibility(JavaVisibility.PUBLIC);
        commentGenerator.addJavaFileComment(interfaze);
        this.addBasicInterface(interfaze);
        List<CompilationUnit> answer = new ArrayList<>();
        answer.add(interfaze);
        List<CompilationUnit> extraCompilationUnits = this.getExtraCompilationUnits();
        if (extraCompilationUnits != null) {
            answer.addAll(extraCompilationUnits);
        }
        return answer;
    }

    protected void addBasicInterface(Interface interfaze) {
        List<IntrospectedColumn> introspectedColumns = this.introspectedTable.getPrimaryKeyColumns();
        if (introspectedColumns.isEmpty()) {
            return;
        }
        FullyQualifiedJavaType entity = new FullyQualifiedJavaType(this.introspectedTable.getBaseRecordType());
        interfaze.addImportedType(entity);
        IntrospectedColumn introspectedColumn = introspectedColumns.get(0);
        FullyQualifiedJavaType key = introspectedColumn.getFullyQualifiedJavaType();
        interfaze.addImportedType(key);
        // 基类
        FullyQualifiedJavaType baseService = new FullyQualifiedJavaType("com.strr.base.service.SCrudService");
        baseService.addTypeArgument(entity);
        baseService.addTypeArgument(key);
        interfaze.addSuperInterface(baseService);
        interfaze.addImportedType(baseService);
    }

    // serviceImpl
    public List<CompilationUnit> getExtraCompilationUnits() {
        JavaBaseServiceImplGenerator javaBaseServiceImplGenerator = new JavaBaseServiceImplGenerator(this.getProject());
        javaBaseServiceImplGenerator.setContext(this.context);
        javaBaseServiceImplGenerator.setIntrospectedTable(this.introspectedTable);
        javaBaseServiceImplGenerator.setProgressCallback(this.progressCallback);
        javaBaseServiceImplGenerator.setWarnings(this.warnings);
        return javaBaseServiceImplGenerator.getCompilationUnits();
    }
}
