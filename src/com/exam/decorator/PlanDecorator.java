package com.exam.decorator;

import com.exam.model.LearningPlan;

/**
 * 【4. 装饰者模式 - Decorator Pattern】
 * 
 * 功能说明：
 * 动态增强学习计划的展示效果，为VIP用户提供更丰富的展示内容。
 * 装饰者模式的应用理由：
 * 1. 在不修改原有类的情况下，动态地扩展对象的功能
 * 2. 可以灵活组合多种装饰效果
 * 3. 符合开闭原则，对扩展开放，对修改关闭
 * 
 * 优点：
 * - 动态扩展：运行时动态添加功能
 * - 灵活组合：可以组合多种装饰效果
 * - 不修改原类：保持原有类的稳定性
 * 
 * @author 系统设计
 * @version 1.0
 */
public class PlanDecorator {
    // 被装饰的学习计划对象
    private LearningPlan plan;

    /**
     * 构造函数
     * @param plan 需要装饰的学习计划对象
     */
    public PlanDecorator(LearningPlan plan) {
        this.plan = plan;
        System.out.println("[日志] PlanDecorator 装饰器已创建");
    }

    /**
     * 获取增强后的VIP报告
     * @return 装饰后的VIP报告内容
     */
    public String getVipReport() {
        System.out.println("[日志] PlanDecorator 生成VIP报告");
        StringBuilder sb = new StringBuilder();
        sb.append("\n$$$$$$$$$$ VIP 尊享通道 $$$$$$$$$$\n");
        sb.append("$$  专家审核 · 极速生成 · 考点预测  $$\n");
        sb.append(plan.getReport()); // 调用原始对象逻辑
        sb.append("$$$$$$$$$$ 祝您考试满分 $$$$$$$$$$\n");
        return sb.toString();
    }
}
