package com.exam.factory;

import com.exam.model.LearningTask;

/**
 * 【2. 工厂方法模式】
 * 定义创建对象的标准接口
 */
public interface TaskFactory {
    LearningTask createTask();
}
