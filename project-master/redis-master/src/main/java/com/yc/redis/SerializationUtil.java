//package com.yc.utils;
//
//import com.dyuproject.protostuff.LinkedBuffer;
//import com.dyuproject.protostuff.ProtostuffIOUtil;
//import com.dyuproject.protostuff.Schema;
//import com.dyuproject.protostuff.runtime.RuntimeSchema;
//import org.springframework.objenesis.Objenesis;
//import org.springframework.objenesis.ObjenesisStd;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
///**
// * 应用模块名称<p>
// * 代码描述<p>
// * Copyright: Copyright (C) 2020 XXX, Inc. All rights reserved. <p>
// *
// * @author yuche
// * @since 2020/4/3 22:33
// */
//public class SerializationUtil {
//    private static Map<Class<?>, Schema<?>> cachedSchema = new ConcurrentHashMap<>();
//
//    private static Objenesis objenesis = new ObjenesisStd(true);
//
//    private SerializationUtil() {
//    }
//
//    @SuppressWarnings("unchecked")
//    private static <T> Schema<T> getSchema(Class<T> cls) {
//        Schema<T> schema = (Schema<T>) cachedSchema.get(cls);
//        if (schema == null) {
//            schema = RuntimeSchema.createFrom(cls);
//            if (schema != null) {
//                cachedSchema.put(cls, schema);
//            }
//        }
//        return schema;
//    }
//
//    @SuppressWarnings("unchecked")
//    public static <T> byte[] serialize(T obj) {
//        Class<T> cls = (Class<T>) obj.getClass();
//        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
//        try {
//            Schema<T> schema = getSchema(cls);
//            return ProtostuffIOUtil.toByteArray(obj, schema, buffer);
//        } catch (Exception e) {
//            throw new IllegalStateException(e.getMessage(), e);
//        } finally {
//            buffer.clear();
//        }
//    }
//
//    /**
//     * 序列化列表
//     *
//     * @param objList
//     * @return
//     */
//    public static <T> byte[] serializeList(List<T> objList) {
//        if (objList == null || objList.isEmpty()) {
//            throw new RuntimeException("序列化对象列表(" + objList + ")参数异常!");
//        }
//        @SuppressWarnings("unchecked")
//        Schema<T> schema = (Schema<T>) RuntimeSchema.getSchema(objList.get(0).getClass());
//        LinkedBuffer buffer = LinkedBuffer.allocate(1024 * 1024);
//        byte[] protostuff = null;
//        ByteArrayOutputStream bos = null;
//        try {
//            bos = new ByteArrayOutputStream();
//            ProtostuffIOUtil.writeListTo(bos, objList, schema, buffer);
//            protostuff = bos.toByteArray();
//        } catch (Exception e) {
//            throw new RuntimeException("序列化对象列表(" + objList + ")发生异常!", e);
//        } finally {
//            buffer.clear();
//            try {
//                if (bos != null) {
//                    bos.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        return protostuff;
//    }
//
//    public static <T> T deserialize(byte[] data, Class<T> cls) {
//        try {
//            T message = (T) objenesis.newInstance(cls);
//            Schema<T> schema = getSchema(cls);
//            ProtostuffIOUtil.mergeFrom(data, message, schema);
//            return message;
//        } catch (Exception e) {
//            throw new IllegalStateException(e.getMessage(), e);
//        }
//    }
//
//    /**
//     * 反序列化列表
//     *
//     * @param paramArrayOfByte
//     * @param targetClass
//     * @return
//     */
//    public static <T> List<T> deserializeList(byte[] paramArrayOfByte, Class<T> targetClass) {
//        if (paramArrayOfByte == null || paramArrayOfByte.length == 0) {
//            throw new RuntimeException("反序列化对象发生异常,byte序列为空!");
//        }
//
//        Schema<T> schema = RuntimeSchema.getSchema(targetClass);
//        List<T> result = null;
//        try {
//            result = ProtostuffIOUtil.parseListFrom(new ByteArrayInputStream(paramArrayOfByte), schema);
//        } catch (IOException e) {
//            throw new RuntimeException("反序列化对象列表发生异常!", e);
//        }
//        return result;
//    }
//}
