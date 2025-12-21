package edu.lsu.learning.state;

import edu.lsu.learning.plan.LearningPlan;

public class InProgressState implements LearningState {

    private LearningPlan plan;

    public InProgressState(LearningPlan plan) {
        this.plan = plan;
    }

    @Override
    public void start() {
        System.out.println("学习计划正在执行中。");
    }

    @Override
    public void pause() {
        System.out.println("学习计划已暂停。");
        plan.setState(new PausedState(plan));
    }

    @Override
    public void complete() {
        System.out.println("学习计划已完成。");
        plan.setState(new CompletedState(plan));
    }
}
