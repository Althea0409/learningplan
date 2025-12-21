package com.exam.builder;

import com.exam.factory.ExerciseFactory;
import com.exam.factory.TaskFactory;
import com.exam.factory.VideoFactory;
import com.exam.model.LearningPlan;

/**
 * 【3. 建造者模式 - Builder Pattern】
 * 
 * 功能说明：
 * 定义构建学习计划的抽象接口，将复杂对象的构建过程与表示分离。
 * 建造者模式的应用理由：
 * 1. 学习计划对象构建过程复杂，包含多个步骤
 * 2. 不同的构建方式（基础模式、冲刺模式）需要不同的构建逻辑
 * 3. 将构建过程封装，使客户端代码更简洁
 * 
 * 优点：
 * - 分离构建过程和表示，构建过程可以复用
 * - 可以精细控制构建过程，逐步构建复杂对象
 * - 符合开闭原则，新增构建方式只需添加新的建造者
 * 
 * @author 系统设计
 * @version 1.0
 */
public abstract class PlanBuilder {
    // 被构建的学习计划对象
    protected LearningPlan plan = new LearningPlan();

    // 准备工厂对象（使用工厂方法模式创建任务）
    protected TaskFactory videoFactory = new VideoFactory();
    protected TaskFactory exerciseFactory = new ExerciseFactory();

    /**
     * 构建计划名称（抽象方法，由子类实现）
     */
    public abstract void buildName();

    /**
     * 构建第一天的学习任务（抽象方法，由子类实现）
     */
    public abstract void buildDay1();

    /**
     * 构建第二天的学习任务（抽象方法，由子类实现）
     */
    public abstract void buildDay2();

    /**
     * 获取构建结果
     * @return 构建完成的学习计划对象
     */
    public LearningPlan getResult() {
        System.out.println("[日志] PlanBuilder 构建完成，返回学习计划");
        return plan;
    }
}
