package edu.lsu.learning.plan;

/**
 * 具体产品类 B：侧重理论研究的学习计划
 * * 工厂模式产品族成员
 */
public class TheoryLearningPlan extends LearningPlan {

    public TheoryLearningPlan(String learnerName, String goal) {
        super(learnerName, goal);
    }

    @Override
    public void addLog(String message) {
        // 重写方法：给日志添加特殊标记，体现多态行为
        super.addLog("[Theory 理论] " + message);
    }
}
