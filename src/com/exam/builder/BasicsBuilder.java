package com.exam.builder;

/**
 * 具体建造者 A (策略模式体现：基础巩固)
 */
public class BasicsBuilder extends PlanBuilder {
    @Override
    public void buildName() {
        plan.setPlanName("基础巩固计划 (Basics Mode)");
    }

    @Override
    public void buildDay1() {
        // 基础阶段：多看视频
        plan.addTask(videoFactory.createTask());
        plan.addTask(videoFactory.createTask());
    }

    @Override
    public void buildDay2() {
        // 基础阶段：少做题
        plan.addTask(exerciseFactory.createTask());
    }
}
