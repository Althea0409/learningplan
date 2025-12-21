package com.exam.factory;

import com.exam.core.ResourceCenter;
import com.exam.model.ExerciseTask;
import com.exam.model.LearningTask;

public class ExerciseFactory implements TaskFactory {
    @Override
    public LearningTask createTask() {
        return new ExerciseTask(ResourceCenter.getInstance().getRandomExercise());
    }
}
