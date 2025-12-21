package edu.lsu.learning.strategy;

public class BasicLearningStrategy implements LearningPlanStrategy {

    @Override
    public void generatePlan(String learnerName, String goal) {
        System.out.println("为 " + learnerName + " 生成基础型学习计划，目标：" + goal);
    }
}
