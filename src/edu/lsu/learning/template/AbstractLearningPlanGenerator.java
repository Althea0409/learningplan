package edu.lsu.learning.template;

import edu.lsu.learning.strategy.LearningPlanStrategy;

public abstract class AbstractLearningPlanGenerator {

    /**
     * 模板方法：定义生成流程的骨架
     * 修改点：返回 String 类型的完整报告
     */
    public final String generate(String learnerName, String goal, LearningPlanStrategy strategy) {
        StringBuilder report = new StringBuilder();

        // 步骤 1：分析
        report.append(analyzeGoal(goal)).append("\n");
        // 步骤 2：评估
        report.append(assessLevel()).append("\n");

        // 步骤 3：策略生成 (Strategy)
        String detailedPlan = strategy.generatePlan(learnerName, goal);
        report.append(detailedPlan).append("\n\n");

        // 步骤 4：输出/归档
        report.append(outputPlan());

        return report.toString();
    }

    protected abstract String analyzeGoal(String goal);
    protected abstract String assessLevel();
    protected abstract String outputPlan();
}
