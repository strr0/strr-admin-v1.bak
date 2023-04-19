package com.strr.code.service;

import com.strr.code.config.CustomIntrospectedTable;
import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.codegen.AbstractJavaGenerator;
import org.mybatis.generator.internal.util.messages.Messages;

import java.util.Collections;
import java.util.List;

public class JavaBaseServiceImplGenerator extends AbstractJavaGenerator {
    public JavaBaseServiceImplGenerator(String project) {
        super(project);
    }

    @Override
    public List<CompilationUnit> getCompilationUnits() {
        this.progressCallback.startTask(Messages.getString("Progress.service.impl", this.introspectedTable.getFullyQualifiedTable().toString()));
        CommentGenerator commentGenerator = this.context.getCommentGenerator();
        FullyQualifiedJavaType type = new FullyQualifiedJavaType(((CustomIntrospectedTable)this.introspectedTable).getMyBatis3JavaServiceImplType());
        TopLevelClass topLevelClass = new TopLevelClass(type);
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);
        commentGenerator.addJavaFileComment(topLevelClass);
        this.addBasicInfo(topLevelClass);
        this.addBasicConstructor(topLevelClass);
        this.addBasicClass(topLevelClass);
        return Collections.singletonList(topLevelClass);
    }

    protected void addBasicInfo(TopLevelClass topLevelClass) {
        // service注解
        topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.stereotype.Service"));
        topLevelClass.addAnnotation("@Service");
        // service
        FullyQualifiedJavaType service = new FullyQualifiedJavaType(((CustomIntrospectedTable)this.introspectedTable).getMyBatis3JavaServiceType());
        topLevelClass.addImportedType(service);
        topLevelClass.addSuperInterface(service);
    }

    // 构造函数
    protected void addBasicConstructor(TopLevelClass topLevelClass) {
        String basicName = ((CustomIntrospectedTable)this.introspectedTable).getBasicName();
        // mapper
        FullyQualifiedJavaType mapper = new FullyQualifiedJavaType(this.introspectedTable.getMyBatis3JavaMapperType());
        topLevelClass.addImportedType(mapper);
        Field field = new Field(basicName + "Mapper", mapper);
        field.setVisibility(JavaVisibility.PRIVATE);
        field.setFinal(true);
        topLevelClass.addField(field);
        // 构造函数
        topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.beans.factory.annotation.Autowired"));
        Method method = new Method(topLevelClass.getType().getShortName());
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setConstructor(true);
        method.addParameter(new Parameter(mapper, basicName + "Mapper"));
        method.addBodyLine(String.format("this.%sMapper = %sMapper;", basicName, basicName));
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
        FullyQualifiedJavaType baseServiceImpl = new FullyQualifiedJavaType("com.strr.base.service.impl.SCrudServiceImpl");
        baseServiceImpl.addTypeArgument(entity);
        baseServiceImpl.addTypeArgument(key);
        topLevelClass.setSuperClass(baseServiceImpl);
        topLevelClass.addImportedType(baseServiceImpl);
        // 基类方法
        FullyQualifiedJavaType baseMapper = new FullyQualifiedJavaType("com.strr.base.mapper.SCrudMapper");
        baseMapper.addTypeArgument(entity);
        baseMapper.addTypeArgument(key);
        topLevelClass.addImportedType(baseMapper);
        Method method = new Method("getMapper");
        method.setVisibility(JavaVisibility.PROTECTED);
        method.setReturnType(baseMapper);
        method.addBodyLine(String.format("return %sMapper;", basicName));
        method.addAnnotation("@Override");
        topLevelClass.addMethod(method);
    }
}
