package com.strr.code.mapper;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.codegen.AbstractJavaGenerator;
import org.mybatis.generator.internal.util.messages.Messages;

import java.util.Collections;
import java.util.List;

public class JavaBaseMapperGenerator extends AbstractJavaGenerator {
    public JavaBaseMapperGenerator(String project) {
        super(project);
    }

    @Override
    public List<CompilationUnit> getCompilationUnits() {
        this.progressCallback.startTask(Messages.getString("Progress.mapper", this.introspectedTable.getFullyQualifiedTable().toString()));
        CommentGenerator commentGenerator = this.context.getCommentGenerator();
        FullyQualifiedJavaType type = new FullyQualifiedJavaType(this.introspectedTable.getMyBatis3JavaMapperType());
        Interface interfaze = new Interface(type);
        interfaze.setVisibility(JavaVisibility.PUBLIC);
        interfaze.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.Mapper"));
        interfaze.addAnnotation("@Mapper");
        commentGenerator.addJavaFileComment(interfaze);
        this.addBasicInterface(interfaze);
        return Collections.singletonList(interfaze);
    }

    // 基类信息
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
        FullyQualifiedJavaType baseMapper = new FullyQualifiedJavaType("com.strr.base.mapper.SCrudMapper");
        baseMapper.addTypeArgument(entity);
        baseMapper.addTypeArgument(key);
        interfaze.addSuperInterface(baseMapper);
        interfaze.addImportedType(baseMapper);
    }
}
