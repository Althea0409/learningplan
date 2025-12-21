package com.exam.core;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * 【1. 单例模式】
 * 模拟全局唯一的题库资源中心
 */
public class ResourceCenter {
    // 1. 私有静态实例
    private static ResourceCenter instance = new ResourceCenter();

    // 模拟数据源
    private List<String> videos = Arrays.asList("Java基础语法", "23种设计模式精讲", "JVM底层原理", "高并发编程实战");
    private List<String> exercises = Arrays.asList("手写单例模式", "解释工厂方法", "分析Spring AOP原理", "线程池参数配置");

    // 2. 私有构造
    private ResourceCenter() {}

    // 3. 公共访问点
    public static ResourceCenter getInstance() {
        return instance;
    }

    // 业务方法
    public String getRandomVideo() {
        return videos.get(new Random().nextInt(videos.size()));
    }

    public String getRandomExercise() {
        return exercises.get(new Random().nextInt(exercises.size()));
    }
}
