package com.exam.model;

public class VideoTask implements LearningTask {
    private String name;

    public VideoTask(String name) {
        this.name = "【视频课程】" + name;
    }

    @Override
    public String getContent() {
        return name;
    }
}
