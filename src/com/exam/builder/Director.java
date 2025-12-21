package com.exam.builder;

import com.exam.model.LearningPlan;

/**
 * 指挥者：负责组织构建流程
 */
public class Director {
    public LearningPlan construct(PlanBuilder builder) {
        builder.buildName();
        builder.buildDay1();
        builder.buildDay2();
        return builder.getResult();
    }
}
