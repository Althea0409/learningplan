package edu.lsu.learning.strategy;

public class SprintLearningStrategy implements LearningPlanStrategy {
    @Override
    public String generatePlan(String learnerName, String goal) {
        StringBuilder sb = new StringBuilder();
        sb.append("=== 冲刺型学习计划 (Sprint Plan) ===\n");
        sb.append("学员：").append(learnerName).append("\n");
        sb.append("目标：").append(goal).append("\n");
        sb.append("--------------------------------------------------\n");
        sb.append("Day 1-2：高频考点速记\n");
        sb.append("  - 策略：利用思维导图建立知识骨架，死磕重点。\n");
        sb.append("Day 3-5：真题模拟与实战\n");
        sb.append("  - 策略：刷历年真题，整理错题集，查漏补缺。\n");
        sb.append("Day 6-7：全流程演练\n");
        sb.append("  - 策略：模拟真实环境，调整心态。\n");
        sb.append("--------------------------------------------------\n");
        sb.append("【专家建议】：抓大放小，放弃偏难怪题，确保核心分数。");
        return sb.toString();
    }
}
