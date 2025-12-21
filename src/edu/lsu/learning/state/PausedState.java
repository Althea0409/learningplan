package edu.lsu.learning.state;

import edu.lsu.learning.plan.LearningPlan;

public class PausedState implements LearningState {

    private LearningPlan plan;

    public PausedState(LearningPlan plan) {
        this.plan = plan;
    }

    @Override
    public void start() {
        System.out.println("继续执行学习计划。");
        plan.setState(new InProgressState(plan));
    }

    @Override
    public void pause() {
        System.out.println("学习计划已处于暂停状态。");
    }

    @Override
    public void complete() {
        System.out.println("暂停状态下无法直接完成。");
    }
}
