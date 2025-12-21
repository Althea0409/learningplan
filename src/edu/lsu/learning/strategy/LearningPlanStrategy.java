package edu.lsu.learning.strategy;

/**
 * 策略接口：定义生成学习计划的算法族
 * 修改点：返回值由 void 改为 String，以便将生成的内容返回给调用者显示
 */
public interface LearningPlanStrategy {
    String generatePlan(String learnerName, String goal);
}
