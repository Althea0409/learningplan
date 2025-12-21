package com.exam.core;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * 【1. 单例模式 - Singleton Pattern】
 * 
 * 功能说明：
 * 模拟全局唯一的题库资源中心，确保整个系统中只有一个资源中心实例。
 * 单例模式的应用理由：
 * 1. 资源中心包含题库数据，应该全局唯一，避免重复加载和内存浪费
 * 2. 提供统一的资源访问入口，便于管理和维护
 * 3. 保证数据一致性，所有组件访问的是同一个资源池
 * 
 * 优点：
 * - 节省内存资源，避免重复创建对象
 * - 提供全局访问点，方便资源管理
 * - 保证数据一致性
 * 
 * @author 系统设计
 * @version 1.0
 */
public class ResourceCenter {
    // 1. 私有静态实例（饿汉式单例，线程安全）
    private static ResourceCenter instance = new ResourceCenter();

    // 模拟数据源：视频课程列表
    private List<String> videos = Arrays.asList(
        "Java基础语法", 
        "23种设计模式精讲", 
        "JVM底层原理", 
        "高并发编程实战",
        "Spring框架核心原理",
        "MyBatis源码解析",
        "Redis缓存实战"
    );
    
    // 模拟数据源：练习题列表
    private List<String> exercises = Arrays.asList(
        "手写单例模式", 
        "解释工厂方法", 
        "分析Spring AOP原理", 
        "线程池参数配置",
        "实现观察者模式",
        "设计责任链模式",
        "优化策略模式应用"
    );

    // 2. 私有构造方法，防止外部实例化
    private ResourceCenter() {
        System.out.println("[日志] ResourceCenter 单例实例已创建");
    }

    // 3. 公共静态访问点，获取唯一实例
    public static ResourceCenter getInstance() {
        return instance;
    }

    /**
     * 获取随机视频课程
     * @return 随机视频课程名称
     */
    public String getRandomVideo() {
        String video = videos.get(new Random().nextInt(videos.size()));
        System.out.println("[日志] 获取视频资源: " + video);
        return video;
    }

    /**
     * 获取随机练习题
     * @return 随机练习题名称
     */
    public String getRandomExercise() {
        String exercise = exercises.get(new Random().nextInt(exercises.size()));
        System.out.println("[日志] 获取练习资源: " + exercise);
        return exercise;
    }

    /**
     * 获取视频资源总数
     * @return 视频资源数量
     */
    public int getVideoCount() {
        return videos.size();
    }

    /**
     * 获取练习资源总数
     * @return 练习资源数量
     */
    public int getExerciseCount() {
        return exercises.size();
    }
}
