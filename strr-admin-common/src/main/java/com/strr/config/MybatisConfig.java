package com.strr.config;

import com.strr.config.builder.CrudMapperScannerBuilder;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisConfig {
//    @Value("${strr.base:com.strr}")
    private String basePackage = "com.strr";

    @Autowired
    public MybatisConfig(SqlSessionFactory sqlSessionFactory) {
        CrudMapperScannerBuilder builder = new CrudMapperScannerBuilder(sqlSessionFactory.getConfiguration());
        builder.scanMapper(basePackage);
    }
}
