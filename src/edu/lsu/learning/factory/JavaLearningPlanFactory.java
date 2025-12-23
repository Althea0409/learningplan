package edu.lsu.learning.factory;

import edu.lsu.learning.plan.CodeLearningPlan;
import edu.lsu.learning.plan.LearningPlan;
import edu.lsu.learning.plan.TheoryLearningPlan;

/**
 * 具体工厂类
 * 负责根据业务规则实例化不同的 LearningPlan 子类
 */
public class JavaLearningPlanFactory implements LearningPlanFactory {

    @Override
    public LearningPlan createLearningPlan(String learnerName, String goal) {
        // 简单工厂逻辑：根据目标关键词决定创建哪种对象
        // 如果目标包含编程相关词汇，创建实战型计划
        String lowerGoal = goal.toLowerCase();

        if (lowerGoal.contains("java") ||
                lowerGoal.contains("python") ||
                lowerGoal.contains("code") ||
                lowerGoal.contains("代码") ||
                lowerGoal.contains("编程") ||
                lowerGoal.contains("开发")) {

            System.out.println("[Factory] 识别到编程类目标，创建 CodeLearningPlan");
            return new CodeLearningPlan(learnerName, goal);

        } else {
            // 否则创建理论型计划
            System.out.println("[Factory] 识别为通用/理论目标，创建 TheoryLearningPlan");
            return new TheoryLearningPlan(learnerName, goal);
        }
    }
}
