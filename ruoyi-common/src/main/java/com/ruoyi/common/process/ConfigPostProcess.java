package com.ruoyi.common.process;

import com.ruoyi.common.annotation.Config;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.*;

/**
 * 配置发布过程
 *
 * @author Star
 * @description: TODO
 * @date 2023/4/6 14:23
 */
@Component
public class ConfigPostProcess implements BeanPostProcessor {
    /**
     * 文件名称
     */
    private static final String FILE_NAME = "config.properties";

    /**
     * 发布过程初始化后
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Config annotation = AnnotationUtils.findAnnotation(bean.getClass(), Config.class);
        if(annotation != null){
            Map<String, String> configProperties = getConfigPropertiesFromFile(annotation);
            bindBeanValue(bean,configProperties);
        }
        return bean;
    }

    /**
     * bean绑定值
     *
     * @param bean             豆
     * @param configProperties 配置属性
     */
    private void bindBeanValue(Object bean,Map<String,String> configProperties){
        if(configProperties.size() > 0){
            Class<?> clazz = bean.getClass();
            configProperties.forEach((key,value)->{
                setFieldValueByFieldName(bean,clazz,key,value);
            });
        }
    }

    /**
     * 设置字段值字段名称
     *
     * @param bean  对象
     * @param clazz  对象的class
     * @param key   关键
     * @param value 价值
     */
    private void setFieldValueByFieldName(Object bean,Class<?> clazz,String key,String value){
        if (checkFieldExists(clazz,key)) {
            try {
                Field field = clazz.getDeclaredField(key);
                field.setAccessible(true);
                if(field.getType().equals(String.class)){
                    //字符串类型
                    field.set(bean,value);
                }else if(field.getType().equals(int.class)){
                    //数字类型
                    field.set(bean,Integer.valueOf(value));
                }else if(field.getType().equals(boolean.class)){
                    //布尔类型
                    field.set(bean,Boolean.parseBoolean(value));
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 检查现场存在
     *
     * @param clazz 对象
     * @param key  关键
     * @return boolean
     */
    public boolean checkFieldExists(Class clazz,String key){
        Field[] fields = clazz.getDeclaredFields();
        //判定属性是否存在
        for (Field field : fields) {
            if(field.getName().equals(key)){
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    /**
     * 获取配置属性文件
     *
     * @param annotation 注释
     * @return {@link Map}<{@link String},{@link String}>
     */
    private Map<String,String> getConfigPropertiesFromFile(Config annotation){
        HashMap<String, String> configProperties = new HashMap<>();
        //获取前置
        Properties properties = getClassNameFromResource(FILE_NAME);
        String prefix = annotation.prefix();
        //获取属性名称
        Set<String> names = properties.stringPropertyNames();
        //转为arrayList
        List<String> namesList = new ArrayList<>(names);
        for (String name : namesList) {
            if(name.startsWith(prefix)){
                //截取字符串
                String key = name.substring(name.indexOf(prefix) + prefix.length() + 1);
                String value = properties.getProperty(prefix+"."+key);
                configProperties.put(key,value);
            }
        }
        return configProperties;
    }

    /**
     * 从资源获取类名
     *
     * @param fileName 文件名称
     * @return {@link Properties}
     */
    private Properties getClassNameFromResource(String fileName){
        // 属性类
        Properties properties = new Properties();
        // 获取当前文件的类加载器
        ClassLoader classLoader = ConfigPostProcess.class.getClassLoader();
        // 获取资源输入流
        InputStream resourceAsStream = classLoader.getResourceAsStream(fileName);
        try {
            //加载输入流到属性类
            properties.load(resourceAsStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }


}
