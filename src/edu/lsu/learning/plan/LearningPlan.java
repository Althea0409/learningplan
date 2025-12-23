package edu.lsu.learning.plan;

import edu.lsu.learning.observer.PlanObserver;
import edu.lsu.learning.state.LearningState;
import edu.lsu.learning.state.NotStartedState;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 学习计划类 - Context类（状态模式） & Subject类（观察者模式）
 * * 本类负责管理学习计划的状态，并记录执行日志
 * 使用状态模式实现不同状态下的行为控制
 * 使用观察者模式通知界面刷新
 */
public class LearningPlan {

    private String learnerName;
    private String goal;
    private LearningState state;

    // 存储生成的计划文本
    private String planContent;
    // 状态变更与执行日志
    private List<String> executionLog;

    // --- 观察者模式：维护观察者列表 ---
    private List<PlanObserver> observers = new ArrayList<>();

    // 状态名称映射表
    private static final Map<String, String> STATE_NAME_MAP = new HashMap<>();
    static {
        STATE_NAME_MAP.put("NotStartedState", "未开始");
        STATE_NAME_MAP.put("InProgressState", "进行中");
        STATE_NAME_MAP.put("PausedState", "已暂停");
        STATE_NAME_MAP.put("CompletedState", "已完成");
    }

    /**
     * 构造函数
     */
    public LearningPlan(String learnerName, String goal) {
        this.learnerName = learnerName;
        this.goal = goal;
        this.executionLog = new ArrayList<>();
        // 初始状态
        this.state = new NotStartedState(this);
        addLog("学习计划已创建，当前状态：未开始");
    }

    // --- 观察者模式：注册与通知方法 ---

    /**
     * 注册观察者
     * @param observer 以此对象为参数的观察者
     */
    public void attach(PlanObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
            System.out.println("[Observer] 新观察者已注册");
        }
    }

    /**
     * 通知所有观察者状态已变更
     */
    private void notifyObservers() {
        for (PlanObserver observer : observers) {
            observer.update(this);
        }
    }

    // --- 状态模式核心方法 ---

    public void setState(LearningState state) {
        this.state = state;

        // 记录状态变更日志
        String stateClassName = state.getClass().getSimpleName();
        String chineseStateName = STATE_NAME_MAP.getOrDefault(stateClassName, stateClassName);

        // 注意：addLog 内部会调用 notifyObservers，所以这里不需要重复调用，
        // 但为了逻辑清晰，我们把日志和状态变更分开看
        addLog("状态流转 -> " + chineseStateName);
        System.out.println("[LearningPlan] 状态变更: " + stateClassName + " -> " + chineseStateName);
    }

    public LearningState getState() {
        return state;
    }

    // --- 委托给 State 处理的方法 ---

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
        // 内容变更也可以视为一种状态变化，通知界面
        notifyObservers();
    }

    public String getPlanContent() {
        return planContent;
    }

    /**
     * 添加执行日志并通知观察者
     */
    public void addLog(String message) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String logEntry = "[" + sdf.format(new Date()) + "] " + message;
        this.executionLog.add(logEntry);
        System.out.println("[LearningPlan] 添加日志: " + logEntry);

        // 关键：数据发生变化，通知观察者刷新
        notifyObservers();
    }

    public String getLatestLog() {
        if (executionLog.isEmpty()) {
            return "";
        }
        return executionLog.get(executionLog.size() - 1);
    }

    public String getLearnerName() {
        return learnerName;
    }

    public String getGoal() {
        return goal;
    }
}
