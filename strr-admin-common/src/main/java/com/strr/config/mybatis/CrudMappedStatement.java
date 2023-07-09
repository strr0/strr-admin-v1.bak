package com.strr.config.mybatis;

import com.strr.base.exception.BuilderException;
import com.strr.base.mapper.SCrudMapper;
import com.strr.util.EntityUtil;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.session.Configuration;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CrudMappedStatement {
    private Configuration configuration;
    private CrudSqlSource crudSqlSource;
    private Class<?> mapperInterface;
    private Class<?> clazz;
    private List<ResultMap> resultMaps;
    private List<ResultMap> simpleResultMaps;
    private boolean build = false;

    private CrudMappedStatement() {}

    public void addCrudStatement() {
        // 是否生成Crud方法
        if (!build) {
            return;
        }
        // 获取BaseMapper的泛型参数
        addCountByParamStatement();
        addListByParamStatement();
        addSaveStatement();
        addUpdateStatement();
        addRemoveStatement();
        addGetStatement();
    }

    public void addCountByParamStatement() {
        SqlSource countByParam = crudSqlSource.countByParamSqlSource();
        MappedStatement.Builder mappedStatementBuilder = new MappedStatement.Builder(
                configuration,
                String.format("%s.countByParam", mapperInterface.getTypeName()),
                countByParam,
                SqlCommandType.SELECT
        );
        mappedStatementBuilder.resultMaps(simpleResultMaps);
        configuration.addMappedStatement(mappedStatementBuilder.build());
    }

    public void addListByParamStatement() {
        SqlSource listByParam = crudSqlSource.listByParamSqlSource();
        MappedStatement.Builder mappedStatementBuilder = new MappedStatement.Builder(
                configuration,
                String.format("%s.listByParam", mapperInterface.getTypeName()),
                listByParam,
                SqlCommandType.SELECT
        );
        mappedStatementBuilder.resultMaps(resultMaps);
        configuration.addMappedStatement(mappedStatementBuilder.build());
    }

    public void addSaveStatement() {
        SqlSource save = crudSqlSource.saveSqlSource();
        MappedStatement.Builder mappedStatementBuilder = new MappedStatement.Builder(
                configuration,
                String.format("%s.save", mapperInterface.getTypeName()),
                save,
                SqlCommandType.INSERT
        );
        mappedStatementBuilder.resultMaps(simpleResultMaps);
        configuration.addMappedStatement(mappedStatementBuilder.build());
    }

    public void addUpdateStatement() {
        try {
            SqlSource update = crudSqlSource.updateSqlSource();
            MappedStatement.Builder mappedStatementBuilder = new MappedStatement.Builder(
                    configuration,
                    String.format("%s.update", mapperInterface.getTypeName()),
                    update,
                    SqlCommandType.UPDATE
            );
            mappedStatementBuilder.resultMaps(simpleResultMaps);
            configuration.addMappedStatement(mappedStatementBuilder.build());
        } catch (BuilderException e) {
            e.printStackTrace();
        }
    }

    public void addRemoveStatement() {
        try {
            SqlSource remove = crudSqlSource.removeSqlSource();
            MappedStatement.Builder mappedStatementBuilder = new MappedStatement.Builder(
                    configuration,
                    String.format("%s.remove", mapperInterface.getTypeName()),
                    remove,
                    SqlCommandType.DELETE
            );
            mappedStatementBuilder.resultMaps(simpleResultMaps);
            configuration.addMappedStatement(mappedStatementBuilder.build());
        } catch (BuilderException e) {
            e.printStackTrace();
        }
    }

    public void addGetStatement() {
        try {
            SqlSource get = crudSqlSource.getSqlSource();
            MappedStatement.Builder mappedStatementBuilder = new MappedStatement.Builder(
                    configuration,
                    String.format("%s.get", mapperInterface.getTypeName()),
                    get,
                    SqlCommandType.SELECT
            );
            mappedStatementBuilder.resultMaps(resultMaps);
            configuration.addMappedStatement(mappedStatementBuilder.build());
        } catch (BuilderException e) {
            e.printStackTrace();
        }
    }

    public static class Builder {
        private final CrudMappedStatement crudMappedStatement = new CrudMappedStatement();

        public Builder(Configuration configuration, Class<?> mapperInterface) {
            this.crudMappedStatement.configuration = configuration;
            this.crudMappedStatement.mapperInterface = mapperInterface;
        }

        private void preBuild() {
            Class<?> mapperInterface = this.crudMappedStatement.mapperInterface;
            // 是否实现CrudMapper接口
            if (SCrudMapper.class.equals(mapperInterface) || !SCrudMapper.class.isAssignableFrom(mapperInterface)) {
                return;
            }
            // 获取CrudMapper的泛型参数
            Class<?> clazz = (Class<?>) ((ParameterizedType) mapperInterface.getGenericInterfaces()[0]).getActualTypeArguments()[0];
            this.crudMappedStatement.clazz = clazz;
            // 结果集映射
            String entity = clazz.getSimpleName();
            List<ResultMapping> resultMappings = new ArrayList<>();
            for (Field field : clazz.getDeclaredFields()) {
                String property = field.getName();
                String column = EntityUtil.getColumn(field);
                resultMappings.add(new ResultMapping.Builder(this.crudMappedStatement.configuration, property, column, field.getType()).build());
            }
            this.crudMappedStatement.resultMaps = Collections.singletonList(new ResultMap.Builder(this.crudMappedStatement.configuration, entity, clazz, resultMappings).build());
            this.crudMappedStatement.simpleResultMaps = Collections.singletonList(new ResultMap.Builder(this.crudMappedStatement.configuration, entity, int.class, new ArrayList<>()).build());
            this.crudMappedStatement.crudSqlSource = new CrudSqlSource.Builder(this.crudMappedStatement.configuration, clazz).build();
            this.crudMappedStatement.build = true;
        }

        public CrudMappedStatement build() {
            this.preBuild();
            return crudMappedStatement;
        }
    }
}
