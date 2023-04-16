package com.strr.util;

import com.strr.base.annotation.SColumn;
import com.strr.base.annotation.SId;
import com.strr.base.annotation.STable;
import com.strr.base.exception.KeyNotFoundException;

import java.lang.reflect.Field;

public class EntityUtil {
    /**
     * 获取表名
     * @param clazz
     * @return
     */
    public static String getTable(Class<?> clazz) {
        if (clazz.isAnnotationPresent(STable.class)) {
            return clazz.getAnnotation(STable.class).value();
        }
        return clazz.getSimpleName();
    }

    /**
     * 获取字段名
     * @param field
     * @return
     */
    public static String getColumn(Field field) {
        if (field.isAnnotationPresent(SColumn.class)) {
            return field.getAnnotation(SColumn.class).value();
        }
        return field.getName();
    }

    /**
     * 是否主键
     * @param field
     * @return
     */
    public static boolean isKey(Field field) {
        return field.isAnnotationPresent(SId.class);
    }

    /**
     * 是否有主键
     * @param fields
     * @return
     */
    public static boolean hasKey(Field[] fields) {
        for (Field field : fields) {
            if (field.isAnnotationPresent(SId.class)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取主键
     * @param fields
     * @return
     */
    public static Field getKey(Field[] fields) throws KeyNotFoundException {
        for (Field field : fields) {
            if (field.isAnnotationPresent(SId.class)) {
                return field;
            }
        }
        throw new KeyNotFoundException();
    }
}
