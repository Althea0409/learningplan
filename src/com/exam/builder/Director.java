package com.exam.builder;

import com.exam.model.LearningPlan;

/**
 * 【建造者模式 - 指挥者类】
 * 负责组织学习计划的构建流程，定义构建步骤的顺序
 * 
 * @author 系统设计
 * @version 1.0
 */
public class Director {
    /**
     * 构建学习计划（模板方法，定义构建流程）
     * @param builder 具体的建造者对象
     * @return 构建完成的学习计划对象
     */
    public LearningPlan construct(PlanBuilder builder) {
        System.out.println("[日志] Director 开始构建学习计划");
        
        // 步骤1：构建计划名称
        builder.buildName();
        System.out.println("[日志] Director 步骤1：计划名称已构建");
        
        // 步骤2：构建第一天的任务
        builder.buildDay1();
        System.out.println("[日志] Director 步骤2：第一天任务已构建");
        
        // 步骤3：构建第二天的任务
        builder.buildDay2();
        System.out.println("[日志] Director 步骤3：第二天任务已构建");
        
        // 返回构建结果
        LearningPlan plan = builder.getResult();
        System.out.println("[日志] Director 学习计划构建完成");
        return plan;
    }
}
