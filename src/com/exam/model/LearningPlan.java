package com.exam.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 复杂产品对象：学习计划
 */
public class LearningPlan {
    private List<LearningTask> tasks = new ArrayList<>();
    private String planName;

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public void addTask(LearningTask task) {
        tasks.add(task);
    }

    // 获取纯文本报告 (供 GUI 显示使用)
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

    // 控制台打印方法 (保留用于测试)
    public void show() {
        System.out.println(getReport());
    }
}
