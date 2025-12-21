package com.exam.factory;

import com.exam.core.ResourceCenter;
import com.exam.model.ExerciseTask;
import com.exam.model.LearningTask;

/**
 * 【工厂方法模式 - 具体工厂B】
 * 练习任务工厂：负责创建练习学习任务对象
 * 
 * @author 系统设计
 * @version 1.0
 */
public class ExerciseFactory implements TaskFactory {
    @Override
    public LearningTask createTask() {
        // 从单例资源中心获取随机练习资源
        String exerciseName = ResourceCenter.getInstance().getRandomExercise();
        System.out.println("[日志] ExerciseFactory 创建练习任务: " + exerciseName);
        return new ExerciseTask(exerciseName);
    }
}
