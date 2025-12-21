package com.exam.model;

/**
 * 【模型接口 - 学习任务】
 * 
 * 功能说明：
 * 学习任务接口，定义学习任务的基本行为。
 * 使用接口实现多态，支持不同类型的任务（视频、练习等）。
 * 
 * @author 系统设计
 * @version 1.0
 */
public interface LearningTask {
    /**
     * 获取任务内容描述
     * @return 任务内容字符串
     */
    String getContent();
}
