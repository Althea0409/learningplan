package edu.lsu.learning.template;

public class DefaultLearningPlanGenerator extends AbstractLearningPlanGenerator {

    @Override
    protected String analyzeGoal(String goal) {
        return ">>> [系统分析] 正在解析学习目标：“" + goal + "”... 分析完毕。";
    }

    @Override
    protected String assessLevel() {
        return ">>> [系统评估] 根据历史数据，评估学习者基础为：待提升。";
    }

    @Override
    protected String outputPlan() {
        return ">>> [系统归档] 计划生成完毕，已存入数据库。";
    }
}
