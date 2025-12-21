package edu.lsu.learning.factory;

import edu.lsu.learning.plan.LearningPlan;

public interface LearningPlanFactory {
    LearningPlan createLearningPlan(String learnerName, String goal);
}
