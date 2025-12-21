package com.exam.decorator;

import com.exam.model.LearningPlan;

/**
 * 【5. 装饰者模式】
 * 动态增强展示效果 (VIP视图)
 */
public class PlanDecorator {
    private LearningPlan plan;

    public PlanDecorator(LearningPlan plan) {
        this.plan = plan;
    }

    // 获取增强后的文本报告
    public String getVipReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n$$$$$$$$$$ VIP 尊享通道 $$$$$$$$$$\n");
        sb.append("$$  专家审核 · 极速生成 · 考点预测  $$\n");
        sb.append(plan.getReport()); // 调用原始对象逻辑
        sb.append("$$$$$$$$$$ 祝您考试满分 $$$$$$$$$$\n");
        return sb.toString();
    }
}
