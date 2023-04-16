package com.strr.config;

import com.strr.config.builder.CrudMapperScannerBuilder;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MybatisConfig {
//    @Value("${strr.base:com.strr}")
    private String basePackage = "com.strr";

    @Autowired
    public MybatisConfig(SqlSessionFactory sqlSessionFactory) {
        Configuration configuration = sqlSessionFactory.getConfiguration();
        CrudMapperScannerBuilder builder = new CrudMapperScannerBuilder(configuration);
        builder.scanMapper(basePackage);
    }
}
