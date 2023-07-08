package com.strr.config.mybatis;

import com.strr.base.exception.BuilderException;
import com.strr.base.exception.KeyNotFoundException;
import com.strr.util.EntityUtil;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.defaults.RawSqlSource;
import org.apache.ibatis.scripting.xmltags.*;
import org.apache.ibatis.session.Configuration;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CrudSqlSource {
    private Configuration configuration;
    private Class<?> clazz;
    private Field[] fields;
    private String table;
    private SqlNode applyWhere;

    private CrudSqlSource() {}

    public SqlSource countByParamSqlSource() {
        SqlNode sqlNode = new StaticTextSqlNode(countSql().toString());
        return new DynamicSqlSource(configuration, new MixedSqlNode(Arrays.asList(sqlNode, applyWhere)));
    }

    public SqlSource listByParamSqlSource() {
        SqlNode sqlNode = new StaticTextSqlNode(listSql().toString());
        return new DynamicSqlSource(configuration, new MixedSqlNode(Arrays.asList(sqlNode, applyWhere)));
    }

    public SqlSource saveSqlSource() {
        SqlNode sqlNode = new StaticTextSqlNode(saveSql().toString());
        return new RawSqlSource(configuration, sqlNode, clazz);
    }

    public SqlSource updateSqlSource() throws BuilderException {
        SqlNode sqlNode = new StaticTextSqlNode(updateSql().toString());
        return new RawSqlSource(configuration, sqlNode, clazz);
    }

    public SqlSource removeSqlSource() throws BuilderException {
        SqlNode sqlNode = new StaticTextSqlNode(removeSql().toString());
        return new RawSqlSource(configuration, sqlNode, clazz);
    }

    public SqlSource getSqlSource() throws BuilderException {
        SqlNode sqlNode = new StaticTextSqlNode(getSql().toString());
        return new RawSqlSource(configuration, sqlNode, clazz);
    }

    private SQL countSql() {
        SQL sql = new SQL();
        sql.SELECT("count(1)");
        sql.FROM(table);
        return sql;
    }

    private SQL listSql() {
        SQL sql = new SQL();
        for (Field field : fields) {
            sql.SELECT(EntityUtil.getColumn(field));
        }
        sql.FROM(table);
        return sql;
    }

    private SQL saveSql() {
        SQL sql = new SQL();
        sql.INSERT_INTO(table);
        for (Field field : fields) {
            if (EntityUtil.isKey(field)) {
                continue;
            }
            sql.VALUES(EntityUtil.getColumn(field), String.format("#{%s}", field.getName()));
        }
        return sql;
    }

    private SQL updateSql() throws KeyNotFoundException {
        Field key = EntityUtil.getKey(fields);
        String keyColumn = EntityUtil.getColumn(key);
        String keyProperty = key.getName();
        SQL sql = new SQL();
        sql.UPDATE(table);
        for (Field field : fields) {
            if (EntityUtil.isKey(field)) {
                continue;
            }
            sql.SET(String.format("%s = #{%s}", EntityUtil.getColumn(field), field.getName()));
        }
        sql.WHERE(String.format("%s = #{%s}", keyColumn, keyProperty));
        return sql;
    }

    private SQL removeSql() throws KeyNotFoundException {
        Field key = EntityUtil.getKey(fields);
        String keyColumn = EntityUtil.getColumn(key);
        String keyProperty = key.getName();
        SQL sql = new SQL();
        sql.DELETE_FROM(table);
        sql.WHERE(String.format("%s = #{%s}", keyColumn, keyProperty));
        return sql;
    }

    private SQL getSql() throws KeyNotFoundException {
        Field key = EntityUtil.getKey(fields);
        String keyColumn = EntityUtil.getColumn(key);
        String keyProperty = key.getName();
        SQL sql = new SQL();
        for (Field field : fields) {
            sql.SELECT(EntityUtil.getColumn(field));
        }
        sql.FROM(table);
        sql.WHERE(String.format("%s = #{%s}", keyColumn, keyProperty));
        return sql;
    }

    public static class Builder {
        private final CrudSqlSource crudSqlSource = new CrudSqlSource();

        public Builder(Configuration configuration, Class<?> clazz) {
            this.crudSqlSource.configuration = configuration;
            this.crudSqlSource.clazz = clazz;
        }

        private void preBuild() {
            Field[] fields = this.crudSqlSource.clazz.getDeclaredFields();
            this.crudSqlSource.fields = fields;
            // 表名
            this.crudSqlSource.table = EntityUtil.getTable(this.crudSqlSource.clazz);
            // 查询条件
            List<SqlNode> ifNodes = new ArrayList<>();
            for (Field field : fields) {
                String property = field.getName();
                String column = EntityUtil.getColumn(field);
                ifNodes.add(new IfSqlNode(new TextSqlNode(String.format(" and %s = #{%s} ", column, property)), String.format("%s != null && %s != ''", property, property)));
            }
            this.crudSqlSource.applyWhere = new WhereSqlNode(this.crudSqlSource.configuration, new MixedSqlNode(ifNodes));
        }

        public CrudSqlSource build() {
            this.preBuild();
            return crudSqlSource;
        }
    }
}
