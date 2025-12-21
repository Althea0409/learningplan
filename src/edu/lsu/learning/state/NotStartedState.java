package edu.lsu.learning.state;

import edu.lsu.learning.plan.LearningPlan;

public class NotStartedState implements LearningState {

    private LearningPlan plan;

    public NotStartedState(LearningPlan plan) {
        this.plan = plan;
    }

    @Override
    public void start() {
        System.out.println("学习计划开始执行。");
        plan.setState(new InProgressState(plan));
    }

    @Override
    public void pause() {
        System.out.println("计划尚未开始，无法暂停。");
    }

    @Override
    public void complete() {
        System.out.println("计划尚未开始，无法完成。");
    }
}
