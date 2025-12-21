package edu.lsu.learning.template;

import edu.lsu.learning.strategy.LearningPlanStrategy;

public abstract class AbstractLearningPlanGenerator {

    public final void generate(String learnerName, String goal, LearningPlanStrategy strategy) {
        analyzeGoal(goal);
        assessLevel();
        strategy.generatePlan(learnerName, goal);
        outputPlan();
    }

    protected abstract void analyzeGoal(String goal);

    protected abstract void assessLevel();

    protected abstract void outputPlan();
}
