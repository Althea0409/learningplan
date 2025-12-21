package com.exam.builder;

import com.exam.factory.ExerciseFactory;
import com.exam.factory.TaskFactory;
import com.exam.factory.VideoFactory;
import com.exam.model.LearningPlan;

/**
 * 【3. 建造者模式】
 * 定义构建流程
 */
public abstract class PlanBuilder {
    protected LearningPlan plan = new LearningPlan();

    // 准备工厂
    protected TaskFactory videoFactory = new VideoFactory();
    protected TaskFactory exerciseFactory = new ExerciseFactory();

    public abstract void buildName();
    public abstract void buildDay1();
    public abstract void buildDay2();

    public LearningPlan getResult() {
        return plan;
    }
}
