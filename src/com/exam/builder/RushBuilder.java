package com.exam.builder;

/**
 * 具体建造者 B (策略模式体现：高分冲刺)
 */
public class RushBuilder extends PlanBuilder {
    @Override
    public void buildName() {
        plan.setPlanName("高分冲刺计划 (Rush Mode)");
    }

    @Override
    public void buildDay1() {
        plan.addTask(videoFactory.createTask());
        plan.addTask(exerciseFactory.createTask());
    }

    @Override
    public void buildDay2() {
        // 冲刺阶段：高强度刷题
        plan.addTask(exerciseFactory.createTask());
        plan.addTask(exerciseFactory.createTask());
    }
}
