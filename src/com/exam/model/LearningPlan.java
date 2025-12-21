package com.exam.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 【模型类 - 学习计划】
 * 
 * 功能说明：
 * 学习计划实体类，包含计划名称和任务列表。
 * 这是建造者模式构建的复杂产品对象。
 * 
 * @author 系统设计
 * @version 1.0
 */
public class LearningPlan {
    // 学习任务列表
    private List<LearningTask> tasks = new ArrayList<>();
    
    // 计划名称
    private String planName;

    /**
     * 设置计划名称
     * @param planName 计划名称
     */
    public void setPlanName(String planName) {
        this.planName = planName;
        System.out.println("[日志] LearningPlan 设置计划名称: " + planName);
    }

    /**
     * 添加学习任务
     * @param task 学习任务对象
     */
    public void addTask(LearningTask task) {
        tasks.add(task);
        System.out.println("[日志] LearningPlan 添加任务: " + task.getContent());
    }

    /**
     * 获取纯文本报告 (供 GUI 显示使用)
     * @return 格式化的报告字符串
     */
    public String getReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("====== ").append(planName).append(" ======\n");
        if (tasks.isEmpty()) {
            sb.append("(暂无任务)\n");
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                sb.append("Step ").append(i + 1).append(": ")
                        .append(tasks.get(i).getContent()).append("\n");
            }
        }
        sb.append("===============================\n");
        return sb.toString();
    }

    /**
     * 控制台打印方法 (保留用于测试)
     */
    public void show() {
        System.out.println(getReport());
    }

    /**
     * 获取计划名称
     * @return 计划名称
     */
    public String getPlanName() {
        return planName;
    }

    /**
     * 获取任务数量
     * @return 任务数量
     */
    public int getTaskCount() {
        return tasks.size();
    }

    /**
     * 获取任务列表（返回副本，保护内部数据）
     * @return 任务列表
     */
    public List<LearningTask> getTasks() {
        return new ArrayList<>(tasks);
    }
}
