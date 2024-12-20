package com.strr.system.config;

import com.strr.base.config.mybatis.CrudMapperFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = { "com.strr.system.mapper" }, factoryBean = CrudMapperFactoryBean.class)
public class MybatisConfig {
}
