package edu.lsu.learning.observer;

import edu.lsu.learning.plan.LearningPlan;

/**
 * 观察者接口
 * 用于监听学习计划的状态变化
 * * 设计模式：Observer Pattern (观察者模式)
 */
public interface PlanObserver {
    /**
     * 当被观察的主题(Subject)发生变化时，调用此方法更新观察者
     * @param plan 发生变化的学习计划对象
     */
    void update(LearningPlan plan);
}
