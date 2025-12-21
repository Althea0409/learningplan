package edu.lsu.learning.strategy;

public class AdvancedLearningStrategy implements LearningPlanStrategy {
    @Override
    public String generatePlan(String learnerName, String goal) {
        StringBuilder sb = new StringBuilder();
        sb.append("=== 提升型学习计划 (Advanced Plan) ===\n");
        sb.append("学员：").append(learnerName).append("\n");
        sb.append("目标：").append(goal).append("\n");
        sb.append("--------------------------------------------------\n");
        sb.append("阶段一 [第1-2周]：源码剖析与设计模式\n");
        sb.append("  - 任务：深入阅读 Spring/MyBatis 源码，理解 23 种设计模式的应用。\n");
        sb.append("阶段二 [第3-4周]：高并发与性能调优\n");
        sb.append("  - 任务：学习 JUC 包，理解 JVM 内存模型及 GC 算法。\n");
        sb.append("  - 产出：对现有项目进行压测并完成性能优化报告。\n");
        sb.append("--------------------------------------------------\n");
        sb.append("【专家建议】：关注底层原理，多看技术博客，尝试造轮子。");
        return sb.toString();
    }
}
