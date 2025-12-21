package com.exam.model;

/**
 * 【模型类 - 视频任务】
 * 视频学习任务的具体实现类
 * 
 * @author 系统设计
 * @version 1.0
 */
public class VideoTask implements LearningTask {
    private String name;

    /**
     * 构造函数
     * @param name 视频课程名称
     */
    public VideoTask(String name) {
        this.name = "【视频课程】" + name;
        System.out.println("[日志] VideoTask 创建: " + this.name);
    }

    @Override
    public String getContent() {
        return name;
    }
}
