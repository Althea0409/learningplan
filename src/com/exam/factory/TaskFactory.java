package com.exam.factory;

import com.exam.model.LearningTask;

/**
 * 【2. 工厂方法模式 - Factory Method Pattern】
 * 
 * 功能说明：
 * 定义创建学习任务对象的标准接口，将对象的创建延迟到子类。
 * 工厂方法模式的应用理由：
 * 1. 解耦对象的创建和使用，客户端不需要知道具体创建哪个类
 * 2. 支持扩展，新增任务类型只需添加新的工厂实现类
 * 3. 符合开闭原则，对扩展开放，对修改关闭
 * 
 * 优点：
 * - 解耦：客户端代码与具体产品类解耦
 * - 扩展性：新增产品类型只需添加新的工厂类
 * - 符合单一职责原则：每个工厂只负责创建一种产品
 * 
 * @author 系统设计
 * @version 1.0
 */
public interface TaskFactory {
    /**
     * 创建学习任务对象
     * @return 学习任务对象
     */
    LearningTask createTask();
}
