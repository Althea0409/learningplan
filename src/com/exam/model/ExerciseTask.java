package com.exam.model;

public class ExerciseTask implements LearningTask {
    private String name;

    public ExerciseTask(String name) {
        this.name = "【实战习题】" + name;
    }

    @Override
    public String getContent() {
        return name;
    }
}
