package org.warnsun;

import lombok.NonNull;
import org.springframework.util.CollectionUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * 两个bean 之间的转换<br/>
 * <strong>只判断2个对应属性名称是否相同，其类型并不判断</strong>
 * <strong>类名不要用$ 符号</strong>
 *
 * @author song.wang
 * @create 2017/7/28 11:21
 */
public class GenerateClaz {

    private GenerateClaz(){}

    /**
     *
     * @param origin 原始的类
     * @param target 要转换成的类
     * @param path  转换文件的路径
     */
    public static void start(Class origin,Class target,String path){
        try {
            writeFile(origin, target, path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 写文件
     * @param origin
     * @param target
     * @param path
     */
    private static void writeFile(Class origin,Class target,String path) throws IOException{
        List<Method> originGetter = getStartWithSomethingMethod(origin, "get", true, false, "is");
        List<Method> targetSetter = getStartWithSomethingMethod(target, "set", true, true, null);
        File file = new File(path + File.separator + "Convert.java");
        boolean flag = false;
        if (!file.exists())
            flag = file.createNewFile();
        if (!flag)
            return;
        try (FileWriter fileWriter = new FileWriter(file)){
            try(BufferedWriter writer = new BufferedWriter(fileWriter)){
                String targetClazName = getClassName(target);
                String originClazName = getClassName(origin);
                writer.write("public class Convert {");
                writer.newLine();
                writer.write(String.format("\tpublic static %s convert(%s  origin){",targetClazName,originClazName));
                writer.newLine();
                writer.write(String.format("\t\t%s instance = new %s();",targetClazName,targetClazName));
                writer.newLine();
                if (!CollectionUtils.isEmpty(originGetter)
                        && !CollectionUtils.isEmpty(targetSetter)){
                    for (Method targetSetMethod : targetSetter) {
                        for (Method originGetMethod : originGetter) {
                            if (targetSetMethod.getName().substring(1).equals(originGetMethod.getName().substring(1))
                                    || (originGetMethod.getName().startsWith("is") && targetSetMethod.getName().substring(3).equals(originGetMethod.getName().substring(2)))) {
                                writer.write(String.format("\t\tinstance.%s(origin.%s());",targetSetMethod.getName(),originGetMethod.getName()));
                                writer.newLine();
                            }
                        }
                    }
                    writer.write("\t\treturn instance;");
                    writer.newLine();
                }
                writer.write("\t}");
                writer.newLine();
                writer.write("}");
                writer.flush();
            }
        }
    }

    /**
     * 解决 静态内部类的问题
     * @param claz
     * @return
     */
    private static String getClassName(Class claz){
        String name = claz.getName();
        return name.replaceAll("[$]", ".");
    }

    /**
     * 获取指定的方法
     *
     * @param aClass
     * @param start
     * @param isPublic
     * @param returnVoid
     * @param anotherStart
     * @return
     */
    private static List<Method> getStartWithSomethingMethod(@NonNull Class aClass, String start, Boolean isPublic, Boolean returnVoid, String anotherStart) {
        judgeStart(start);
        ArrayList<Method> methods = new ArrayList<>();
        Method[] methods1 = aClass.getMethods();
        for (int i = 0; i < methods1.length; i++) {
            matchMethod(methods1[i].getName().startsWith(start)
                    || (anotherStart != null && methods1[i].getName().startsWith(anotherStart)), isPublic, returnVoid, methods, methods1[i]);
        }
        return methods;
    }

    private static void matchMethod(boolean b, Boolean isPublic, Boolean returnVoid, ArrayList<Method> methods, Method method) {
        if (b) {
            if (isPublic != null) {
                if ((isPublic && (method.getModifiers() & Modifier.PUBLIC) != 0)//如果public
                        ||(!isPublic && (method.getModifiers() & Modifier.PUBLIC) == 0))// if not public
                    judgeReturn(returnVoid, methods, method);
            } else
                judgeReturn(returnVoid, methods, method);
        }
    }

    /**
     *  为空报错
     * @param start
     */
    private static void judgeStart(String start) {
        if (start == null || start.trim().equals(""))
            throw new IllegalArgumentException("start参数不能为空");
    }

    /**
     * 判断返回类型，并将其放入到指定 list 中
     *
     * @param returnVoid
     * @param methods
     * @param methods
     */
    private static void judgeReturn(Boolean returnVoid, @NonNull ArrayList<Method> methods, @NonNull Method method) {
        if (returnVoid != null) {
            if ((!returnVoid && method.getReturnType() != Void.TYPE)//if public && return a value
                    || (returnVoid && method.getReturnType() == Void.TYPE)) //if public && return void
                methods.add(method);
        } else {
            methods.add(method);
        }
    }
}
