package com.exam.factory;

import com.exam.core.ResourceCenter;
import com.exam.model.LearningTask;
import com.exam.model.VideoTask;

public class VideoFactory implements TaskFactory {
    @Override
    public LearningTask createTask() {
        // 从单例资源中心获取数据
        return new VideoTask(ResourceCenter.getInstance().getRandomVideo());
    }
}
