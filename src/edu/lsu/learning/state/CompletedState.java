package edu.lsu.learning.state;

import edu.lsu.learning.plan.LearningPlan;

public class CompletedState implements LearningState {

    private LearningPlan plan;

    public CompletedState(LearningPlan plan) {
        this.plan = plan;
    }

    @Override
    public void start() {
        System.out.println("学习计划已完成，无法重新开始。");
    }

    @Override
    public void pause() {
        System.out.println("学习计划已完成，无法暂停。");
    }

    @Override
    public void complete() {
        System.out.println("学习计划已完成。");
    }
}
