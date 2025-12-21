package edu.lsu.learning.strategy;

public class SprintLearningStrategy implements LearningPlanStrategy {

    @Override
    public void generatePlan(String learnerName, String goal) {
        System.out.println("为 " + learnerName + " 生成冲刺型学习计划，目标：" + goal);
    }
}
