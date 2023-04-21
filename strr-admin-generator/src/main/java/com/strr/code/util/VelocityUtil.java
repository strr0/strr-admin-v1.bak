package com.strr.code.util;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.mybatis.generator.api.IntrospectedTable;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Properties;

public class VelocityUtil {
    private static final VelocityEngine engine;
    private static final VelocityContext context;

    private static String templateName = "vm/vue.vm";
    private static String project;
    private static String path;

    private static boolean flag = false;

    static {
        Properties p = new Properties();
        p.setProperty("resource.loader", "class");
        p.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        engine = new VelocityEngine();
        engine.init(p);
        context = new VelocityContext();
    }

    public static void setProject(String vueProject) {
        project = vueProject;
    }

    public static void setPath(String filePath) {
        path = filePath;
    }

    public static void setPackage(String packageName) {
        context.put("packageName", packageName);
    }

    public static void ready() {
        flag = true;
    }

    public static void loadContext(IntrospectedTable introspectedTable) {
        if (flag) {
            if (context.containsKey("entityName")) {
                run();
            }
            context.put("entityName", introspectedTable.getFullyQualifiedTable().getDomainObjectName());
            context.put("tableComment", introspectedTable.getRemarks());
            context.put("columnInfoList", introspectedTable.getAllColumns());
        }
    }

    public static void run() {
        if (!flag) {
            return;
        }
        File file = new File(project + '/' + path);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        Template template = engine.getTemplate(templateName, "UTF-8");
        try (FileOutputStream fos = new FileOutputStream(file);
             OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
             BufferedWriter bw = new BufferedWriter(osw)) {
            template.merge(context, bw);
            bw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(templateName + " is finished...");
    }
}
