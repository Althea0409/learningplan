package edu.lsu.learning.strategy;

public class BasicLearningStrategy implements LearningPlanStrategy {
    @Override
    public String generatePlan(String learnerName, String goal) {
        StringBuilder sb = new StringBuilder();
        sb.append("=== 基础型学习计划 (Basic Plan) ===\n");
        sb.append("学员：").append(learnerName).append("\n");
        sb.append("目标：").append(goal).append("\n");
        sb.append("--------------------------------------------------\n");
        sb.append("阶段一 [第1-2周]：基础夯实\n");
        sb.append("  - 任务：阅读官方文档，理解核心概念（变量、循环、OOP）。\n");
        sb.append("  - 产出：完成 50 道基础算法题。\n");
        sb.append("阶段二 [第3-4周]：模块实践\n");
        sb.append("  - 任务：学习常用类库 (Collections, IO) 并编写控制台小工具。\n");
        sb.append("  - 产出：简易图书管理系统。\n");
        sb.append("--------------------------------------------------\n");
        sb.append("【专家建议】：切勿眼高手低，每天保持代码编写量。");
        return sb.toString();
    }
}
