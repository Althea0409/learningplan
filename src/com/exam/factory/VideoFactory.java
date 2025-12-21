package com.exam.factory;

import com.exam.core.ResourceCenter;
import com.exam.model.LearningTask;
import com.exam.model.VideoTask;

/**
 * 【工厂方法模式 - 具体工厂A】
 * 视频任务工厂：负责创建视频学习任务对象
 * 
 * @author 系统设计
 * @version 1.0
 */
public class VideoFactory implements TaskFactory {
    @Override
    public LearningTask createTask() {
        // 从单例资源中心获取随机视频资源
        String videoName = ResourceCenter.getInstance().getRandomVideo();
        System.out.println("[日志] VideoFactory 创建视频任务: " + videoName);
        return new VideoTask(videoName);
    }
}
