package com.exam.model;

/**
 * 【模型类 - 练习任务】
 * 练习学习任务的具体实现类
 * 
 * @author 系统设计
 * @version 1.0
 */
public class ExerciseTask implements LearningTask {
    private String name;

    /**
     * 构造函数
     * @param name 练习题目名称
     */
    public ExerciseTask(String name) {
        this.name = "【实战习题】" + name;
        System.out.println("[日志] ExerciseTask 创建: " + this.name);
    }

    @Override
    public String getContent() {
        return name;
    }
}
