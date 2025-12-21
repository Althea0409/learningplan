package edu.lsu.learning.template;

public class DefaultLearningPlanGenerator extends AbstractLearningPlanGenerator {

    @Override
    protected void analyzeGoal(String goal) {
        System.out.println("分析学习目标：" + goal);
    }

    @Override
    protected void assessLevel() {
        System.out.println("评估学习者当前基础水平。");
    }

    @Override
    protected void outputPlan() {
        System.out.println("学习计划生成完成。");
    }
}
