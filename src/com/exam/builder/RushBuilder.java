package com.exam.builder;

/**
 * 【建造者模式 - 具体建造者B】
 * 高分冲刺计划建造者：构建适合快速提分的学习计划
 * 特点：注重实战练习，高强度刷题，快速提升
 * 
 * @author 系统设计
 * @version 1.0
 */
public class RushBuilder extends PlanBuilder {
    @Override
    public void buildName() {
        plan.setPlanName("高分冲刺计划 (Rush Mode)");
        System.out.println("[日志] RushBuilder 设置计划名称：高分冲刺计划");
    }

    @Override
    public void buildDay1() {
        // 冲刺阶段：视频+练习结合
        System.out.println("[日志] RushBuilder 构建第一天任务（视频+练习）");
        plan.addTask(videoFactory.createTask());
        plan.addTask(exerciseFactory.createTask());
    }

    @Override
    public void buildDay2() {
        // 冲刺阶段：高强度刷题
        System.out.println("[日志] RushBuilder 构建第二天任务（高强度刷题）");
        plan.addTask(exerciseFactory.createTask());
        plan.addTask(exerciseFactory.createTask());
    }
}
