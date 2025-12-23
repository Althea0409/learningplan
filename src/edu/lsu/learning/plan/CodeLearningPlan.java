package edu.lsu.learning.plan;

/**
 * 具体产品类 A：侧重编程实战的学习计划
 * * 工厂模式产品族成员
 */
public class CodeLearningPlan extends LearningPlan {

    public CodeLearningPlan(String learnerName, String goal) {
        super(learnerName, goal);
    }

    @Override
    public void addLog(String message) {
        // 重写方法：给日志添加特殊标记，体现多态行为
        super.addLog("[Code 实战] " + message);
    }
}
