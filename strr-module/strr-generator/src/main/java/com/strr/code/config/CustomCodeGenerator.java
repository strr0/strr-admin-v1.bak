package com.strr.code.config;

import com.strr.code.util.VelocityUtil;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.*;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class CustomCodeGenerator {
    private List<String> warnings;
    private boolean overwrite;
    private Configuration config;

    // 初始化
    public void initialize() {
        warnings = new ArrayList<String>();
        overwrite = true;
        config = new Configuration();
        Yaml yaml = new Yaml();
        try (InputStream in = this.getClass().getClassLoader().getResourceAsStream("generator.yml")) {
            CustomConfiguration configuration = yaml.loadAs(in, CustomConfiguration.class);
            loadConfiguration(configuration);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 加载配置
    private void loadConfiguration(CustomConfiguration configuration) {
        CustomContext context = new CustomContext(null);
        context.setJdbcConnectionConfiguration(loadJDBCConnectionConfiguration(configuration));
        // java
        context.setJavaModelGeneratorConfiguration(loadJavaModelGeneratorConfiguration(configuration));
        context.setTargetPackage(configuration.getTargetPackage());
        context.setTargetProject(configuration.getTargetProject());
        // xml
        context.setSqlMapGeneratorConfiguration(loadSqlMapGeneratorConfiguration(configuration));
        context.setClientProject(configuration.getClientProject());
        context.setCommentGeneratorConfiguration(loadCommentGeneratorConfiguration());
        // vue
        loadVueConfiguration(configuration);
        // table
        for (String table : configuration.getTables()) {
            context.addTable(table);
        }
        config.addContext(context);
    }

    // 加载连接配置
    private JDBCConnectionConfiguration loadJDBCConnectionConfiguration(CustomConfiguration configuration) {
        JDBCConnectionConfiguration jdbcConnectionConfiguration = new JDBCConnectionConfiguration();
        jdbcConnectionConfiguration.setConnectionURL(configuration.getUrl());
        jdbcConnectionConfiguration.setDriverClass(configuration.getDriver());
        jdbcConnectionConfiguration.setUserId(configuration.getUser());
        jdbcConnectionConfiguration.setPassword(configuration.getPassword());
        return jdbcConnectionConfiguration;
    }

    // 加载model配置
    private JavaModelGeneratorConfiguration loadJavaModelGeneratorConfiguration(CustomConfiguration configuration) {
        JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = new JavaModelGeneratorConfiguration();
        javaModelGeneratorConfiguration.setTargetPackage(configuration.getTargetPackage());
        javaModelGeneratorConfiguration.setTargetProject(configuration.getTargetProject());
        return javaModelGeneratorConfiguration;
    }

    // 加载xml配置
    private SqlMapGeneratorConfiguration loadSqlMapGeneratorConfiguration(CustomConfiguration configuration) {
        SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration = new SqlMapGeneratorConfiguration();
        sqlMapGeneratorConfiguration.setTargetProject(configuration.getClientProject());
        return sqlMapGeneratorConfiguration;
    }

    // 加载注释配置
    private CommentGeneratorConfiguration loadCommentGeneratorConfiguration() {
        CommentGeneratorConfiguration commentGeneratorConfiguration = new CommentGeneratorConfiguration();
        commentGeneratorConfiguration.setConfigurationType("com.strr.code.config.CustomCommentGenerator");
        return commentGeneratorConfiguration;
    }

    private void loadVueConfiguration(CustomConfiguration configuration) {
        if (StringUtils.isNotBlank(configuration.getVueProject())) {
            VelocityUtil.setProject(configuration.getVueProject());
            VelocityUtil.setPackage(configuration.getTargetPackage());
            VelocityUtil.ready();
        }
    }

    // 生成代码
    public void generate() {
        try {
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
            myBatisGenerator.generate(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        warnings.forEach(System.err::println);
    }

    public static void run() {
        CustomCodeGenerator generator = new CustomCodeGenerator();
        generator.initialize();
        generator.generate();
        // vue
        VelocityUtil.run();
    }
}
