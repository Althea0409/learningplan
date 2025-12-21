package com.exam.builder;

/**
 * 【建造者模式 - 具体建造者A】
 * 基础巩固计划建造者：构建适合初学者的学习计划
 * 特点：注重理论学习，视频为主，练习为辅
 * 
 * @author 系统设计
 * @version 1.0
 */
public class BasicsBuilder extends PlanBuilder {
    @Override
    public void buildName() {
        plan.setPlanName("基础巩固计划 (Basics Mode)");
        System.out.println("[日志] BasicsBuilder 设置计划名称：基础巩固计划");
    }

    @Override
    public void buildDay1() {
        // 基础阶段：多看视频，理解概念
        System.out.println("[日志] BasicsBuilder 构建第一天任务（视频为主）");
        plan.addTask(videoFactory.createTask());
        plan.addTask(videoFactory.createTask());
    }

    @Override
    public void buildDay2() {
        // 基础阶段：少量练习巩固
        System.out.println("[日志] BasicsBuilder 构建第二天任务（少量练习）");
        plan.addTask(exerciseFactory.createTask());
    }
}
