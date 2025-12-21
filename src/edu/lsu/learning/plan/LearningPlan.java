package edu.lsu.learning.plan;

import edu.lsu.learning.state.LearningState;
import edu.lsu.learning.state.NotStartedState;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

/**
 * Context 类：持有状态，并负责记录日志
 */
public class LearningPlan {

    private String learnerName;
    private String goal;
    private LearningState state;

    // 新增：存储生成的计划文本
    private String planContent;
    // 新增：状态变更与执行日志
    private List<String> executionLog;

    public LearningPlan(String learnerName, String goal) {
        this.learnerName = learnerName;
        this.goal = goal;
        this.executionLog = new ArrayList<>();
        // 初始状态
        this.state = new NotStartedState(this);
        addLog("学习计划已创建，当前状态：未开始");
    }

    public void setState(LearningState state) {
        this.state = state;
        // 记录状态变更日志
        String stateName = state.getClass().getSimpleName();
        addLog("状态流转 -> " + stateName);
    }

    public LearningState getState() {
        return state;
    }

    // 委托给 State 处理
    public void start() {
        state.start();
    }

    public void pause() {
        state.pause();
    }

    public void complete() {
        state.complete();
    }

    // --- 数据存取与日志辅助方法 ---

    public void setPlanContent(String content) {
        this.planContent = content;
    }

    public String getPlanContent() {
        return planContent;
    }

    public void addLog(String message) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        this.executionLog.add("[" + sdf.format(new Date()) + "] " + message);
    }

    public String getLatestLog() {
        if (executionLog.isEmpty()) return "";
        return executionLog.get(executionLog.size() - 1);
    }
}
