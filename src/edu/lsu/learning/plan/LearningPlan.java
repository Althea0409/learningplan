package edu.lsu.learning.plan;

import edu.lsu.learning.state.LearningState;
import edu.lsu.learning.state.NotStartedState;

public class LearningPlan {

    private String learnerName;
    private String goal;
    private LearningState state;

    public LearningPlan(String learnerName, String goal) {
        this.learnerName = learnerName;
        this.goal = goal;
        this.state = new NotStartedState(this);
    }

    public void setState(LearningState state) {
        this.state = state;
    }

    public LearningState getState() {
        return state;
    }

    public String getLearnerName() {
        return learnerName;
    }

    public String getGoal() {
        return goal;
    }

    public void start() {
        state.start();
    }

    public void pause() {
        state.pause();
    }

    public void complete() {
        state.complete();
    }
}
