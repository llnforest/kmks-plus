package com.ruoyi.common.utils.valid;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.ruoyi.common.annotation.ValidCode;
import com.ruoyi.common.annotation.ValidField;
import com.ruoyi.common.exception.user.ValidException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;

import java.lang.reflect.Field;

/**
 * 数据表签名字段有效性验证
 *
 * @author Star
 * @description: TODO
 * @date 2023/5/29 15:42
 */
@Slf4j
public class ValidUtils {

    /**
     * 获取签名
     *
     * @param t t
     * @return {@link String}
     */
    public static <T> String getValid(T t){
        log.info("get entity:{}",t);
        Class<?> aClass = t.getClass();
        StringBuilder stringBuilder = new StringBuilder();
        try{
            Field[] declaredFields = aClass.getDeclaredFields();
            for (Field declaredField : declaredFields) {
                boolean annotationPresent = declaredField.isAnnotationPresent(ValidField.class);
                if(annotationPresent){
                    declaredField.setAccessible(true);
                    stringBuilder.append(ObjectUtil.toString(declaredField.get(t)));
                }
            }

        }catch (IllegalAccessException ex){

        }
        log.info("明文:{}",stringBuilder);
        log.info("签名:{}",DigestUtil.sha256Hex(stringBuilder.toString()));
        return DigestUtil.sha256Hex(stringBuilder.toString());
    }

    /**
     * 设置签名字段
     *
     * @param t t
     */
    public static <T> void setValid(T t){
        log.info("set entity:{}",t);
        Class<?> aClass = t.getClass();
        try{
            Field[] declaredFields = aClass.getDeclaredFields();
            for (Field declaredField : declaredFields) {
                boolean annotationPresent = declaredField.isAnnotationPresent(ValidCode.class);
                if(annotationPresent){
                    declaredField.setAccessible(true);
                    declaredField.set(t,getValid(t));
                    break;
                }
            }
        }catch (IllegalAccessException ex){

        }
    }


    /**
     * 显示签名字段有效性
     *
     * @param v     v
     * @param clazz clazz
     */
    public static <V,T> void setValid(V v, Class<T> clazz){
        log.info("show entity:{}",v);
        Class<?> vClass = v.getClass();
        T entity = BeanUtil.toBean(v,clazz);
        Class<?> aClass = entity.getClass();
        try{
            Field[] declaredFields = aClass.getDeclaredFields();
            for (Field declaredField : declaredFields) {
                boolean annotationPresent = declaredField.isAnnotationPresent(ValidCode.class);
                if(annotationPresent){
                    declaredField.setAccessible(true);
                    // 查找对应的Vo中的Field
                    Field vField = vClass.getDeclaredField(declaredField.getName());
                    vField.setAccessible(true);
                    // 给Vo中的Field赋值
                    vField.set(v,ObjectUtil.toString(declaredField.get(entity)).equals(getValid(entity))?"1":"0");
                    break;
                }
            }
        }catch (IllegalAccessException | NoSuchFieldException ex){

        }
    }

    /**
     * 判断是否签名是否有效
     *
     * @param t t
     * @return boolean
     */
    public static <T> boolean isValid(T t) {
        log.info("is entity:{}",t);
        Class<?> aClass = t.getClass();
        try{
            Field[] declaredFields = aClass.getDeclaredFields();
            for (Field declaredField : declaredFields) {
                boolean annotationPresent = declaredField.isAnnotationPresent(ValidCode.class);
                if(annotationPresent){
                    declaredField.setAccessible(true);
                    String code = ObjectUtil.toString(declaredField.get(t));
                    log.info("code:{}",code);
                    return code.equals(getValid(t));
                }
            }

        }catch (IllegalAccessException ex){

        }
        return false;
    }

    /**
     * 验证有效(无效抛异常)
     *
     * @param t t
     */
    public static <T> void verifyValid(Boolean valid,T t) {

        if(valid && !isValid(t)) throw new ValidException();
    }

}
