package edu.lsu.learning.factory;

import edu.lsu.learning.plan.LearningPlan;

public class JavaLearningPlanFactory implements LearningPlanFactory {

    @Override
    public LearningPlan createLearningPlan(String learnerName, String goal) {
        return new LearningPlan(learnerName, goal);
    }
}
