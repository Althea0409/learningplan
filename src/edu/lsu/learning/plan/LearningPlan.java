/**
 * 学习计划类 - Context类（状态模式）
 * 
 * 本类负责管理学习计划的状态，并记录执行日志
 * 使用状态模式实现不同状态下的行为控制
 * 
 * @author LearningPlan System
 * @version 1.0
 */
package edu.lsu.learning.plan;

import edu.lsu.learning.state.LearningState;
import edu.lsu.learning.state.NotStartedState;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    
    // 状态名称中英文映射表
    private static final Map<String, String> STATE_NAME_MAP = new HashMap<>();
    static {
        STATE_NAME_MAP.put("NotStartedState", "未开始");
        STATE_NAME_MAP.put("InProgressState", "进行中");
        STATE_NAME_MAP.put("PausedState", "已暂停");
        STATE_NAME_MAP.put("CompletedState", "已完成");
    }

    /**
     * 构造函数：创建学习计划对象
     * @param learnerName 学习者姓名
     * @param goal 学习目标
     */
    public LearningPlan(String learnerName, String goal) {
        this.learnerName = learnerName;
        this.goal = goal;
        this.executionLog = new ArrayList<>();
        // 初始状态
        this.state = new NotStartedState(this);
        addLog("学习计划已创建，当前状态：未开始");
        System.out.println("[LearningPlan] 创建学习计划 - 学习者: " + learnerName + ", 目标: " + goal);
    }

    /**
     * 设置学习计划状态
     * 当状态改变时，自动记录状态流转日志
     * @param state 新的状态对象
     */
    public void setState(LearningState state) {
        this.state = state;
        // 记录状态变更日志，使用中文状态名称
        String stateClassName = state.getClass().getSimpleName();
        String chineseStateName = getChineseStateName(stateClassName);
        addLog("状态流转 -> " + chineseStateName);
        System.out.println("[LearningPlan] 状态变更: " + stateClassName + " -> " + chineseStateName);
    }
    
    /**
     * 获取状态的中文名称
     * @param stateClassName 状态的类名（英文）
     * @return 状态的中文名称
     */
    private String getChineseStateName(String stateClassName) {
        return STATE_NAME_MAP.getOrDefault(stateClassName, stateClassName);
    }

    /**
     * 获取当前学习计划状态
     * @return 当前状态对象
     */
    public LearningState getState() {
        return state;
    }

    // --- 委托给 State 处理的方法 ---

    /**
     * 开始学习计划
     * 将操作委托给当前状态对象处理
     */
    public void start() {
        System.out.println("[LearningPlan] 执行开始操作");
        state.start();
    }

    /**
     * 暂停学习计划
     * 将操作委托给当前状态对象处理
     */
    public void pause() {
        System.out.println("[LearningPlan] 执行暂停操作");
        state.pause();
    }

    /**
     * 完成学习计划
     * 将操作委托给当前状态对象处理
     */
    public void complete() {
        System.out.println("[LearningPlan] 执行完成操作");
        state.complete();
    }

    // --- 数据存取与日志辅助方法 ---

    /**
     * 设置学习计划内容
     * @param content 计划内容文本
     */
    public void setPlanContent(String content) {
        this.planContent = content;
        System.out.println("[LearningPlan] 设置计划内容，长度: " + 
            (content != null ? content.length() : 0) + " 字符");
    }

    /**
     * 获取学习计划内容
     * @return 计划内容文本
     */
    public String getPlanContent() {
        return planContent;
    }

    /**
     * 添加执行日志
     * 自动添加时间戳
     * @param message 日志消息
     */
    public void addLog(String message) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String logEntry = "[" + sdf.format(new Date()) + "] " + message;
        this.executionLog.add(logEntry);
        System.out.println("[LearningPlan] 添加日志: " + logEntry);
    }

    /**
     * 获取最新的日志条目
     * @return 最新的日志字符串，如果日志为空则返回空字符串
     */
    public String getLatestLog() {
        if (executionLog.isEmpty()) {
            return "";
        }
        return executionLog.get(executionLog.size() - 1);
    }
    
    /**
     * 获取学习者姓名
     * @return 学习者姓名
     */
    public String getLearnerName() {
        return learnerName;
    }
    
    /**
     * 获取学习目标
     * @return 学习目标
     */
    public String getGoal() {
        return goal;
    }
}
