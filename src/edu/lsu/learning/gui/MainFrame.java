package edu.lsu.learning.gui;

import edu.lsu.learning.factory.JavaLearningPlanFactory;
import edu.lsu.learning.plan.LearningPlan;
import edu.lsu.learning.state.*;
import edu.lsu.learning.strategy.*;
import edu.lsu.learning.template.DefaultLearningPlanGenerator;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MainFrame extends JFrame {

    private JTextField nameField;
    private JTextField goalField;
    private JComboBox<String> strategyBox;
    private JTextArea outputArea;

    // 按钮引用，用于状态控制
    private JButton generateBtn;
    private JButton startBtn;
    private JButton pauseBtn;
    private JButton completeBtn;

    private LearningPlan learningPlan;

    public MainFrame() {
        setTitle("个性化学习计划生成与执行管理系统 - 设计模式期末作业");
        setSize(850, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));

        // 1. 顶部标题
        JLabel titleLabel = new JLabel("个性化学习计划生成与执行管理系统", JLabel.CENTER);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 24));
        titleLabel.setForeground(new Color(60, 63, 65));
        titleLabel.setBorder(new EmptyBorder(20, 10, 20, 10));
        add(titleLabel, BorderLayout.NORTH);

        // 2. 中部面板
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBorder(new EmptyBorder(0, 20, 10, 20));
        add(centerPanel, BorderLayout.CENTER);

        // --- 输入区域 ---
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 15));
        inputPanel.setBorder(BorderFactory.createTitledBorder(" 参数配置 "));

        inputPanel.add(new JLabel("学习者姓名："));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("学习目标："));
        goalField = new JTextField();
        inputPanel.add(goalField);

        inputPanel.add(new JLabel("策略选择："));
        strategyBox = new JComboBox<>(new String[]{
                "基础型策略 (稳扎稳打)",
                "提升型策略 (深度钻研)",
                "冲刺型策略 (考前突击)"
        });
        inputPanel.add(strategyBox);
        centerPanel.add(inputPanel, BorderLayout.NORTH);

        // --- 按钮区域 ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        generateBtn = createButton("生成学习计划");
        startBtn = createButton("开始/继续学习");
        pauseBtn = createButton("暂停学习");
        completeBtn = createButton("完成学习");

        buttonPanel.add(generateBtn);
        buttonPanel.add(startBtn);
        buttonPanel.add(pauseBtn);
        buttonPanel.add(completeBtn);
        centerPanel.add(buttonPanel, BorderLayout.CENTER);

        // --- 输出区域 ---
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);  // 按单词换行，避免单词被截断
        
        // 创建滚动面板，配置滚动条
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder(" 系统运行日志与计划详情 "));
        // 设置滚动条策略：垂直和水平滚动条在需要时显示
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        // 设置滚动速度
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(16);
        // 设置首选大小，确保有足够的显示空间
        scrollPane.setPreferredSize(new Dimension(0, 250));
        
        centerPanel.add(scrollPane, BorderLayout.SOUTH);

        // 3. 事件绑定
        generateBtn.addActionListener(e -> generatePlan());
        startBtn.addActionListener(e -> performAction("start"));
        pauseBtn.addActionListener(e -> performAction("pause"));
        completeBtn.addActionListener(e -> performAction("complete"));

        // 初始化按钮状态
        updateButtonStates(null);
    }

    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        btn.setPreferredSize(new Dimension(140, 35));
        return btn;
    }

    // --- 业务逻辑 ---

    private void generatePlan() {
        String name = nameField.getText().trim();
        String goal = goalField.getText().trim();

        if (name.isEmpty() || goal.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请输入完整信息！", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Factory Pattern: 创建 Plan
        JavaLearningPlanFactory factory = new JavaLearningPlanFactory();
        learningPlan = factory.createLearningPlan(name, goal);

        // Strategy Pattern: 选择策略
        LearningPlanStrategy strategy;
        int idx = strategyBox.getSelectedIndex();
        if (idx == 1) strategy = new AdvancedLearningStrategy();
        else if (idx == 2) strategy = new SprintLearningStrategy();
        else strategy = new BasicLearningStrategy();

        // Template Method Pattern: 生成流程
        DefaultLearningPlanGenerator generator = new DefaultLearningPlanGenerator();
        outputArea.setText("");
        outputArea.append("=== 系统正在初始化 ===\n");

        String planContent = generator.generate(name, goal, strategy);
        learningPlan.setPlanContent(planContent);

        outputArea.append(planContent);
        outputArea.append("\n=== 计划已生成，请点击“开始学习” ===\n\n");

        // 自动滚动到底部，显示最新内容
        scrollToBottom();

        updateButtonStates(learningPlan.getState());
    }

    private void performAction(String action) {
        if (learningPlan == null) return;

        // 执行动作
        switch (action) {
            case "start": learningPlan.start(); break;
            case "pause": learningPlan.pause(); break;
            case "complete": learningPlan.complete(); break;
        }

        // 显示最新日志
        outputArea.append(learningPlan.getLatestLog() + "\n");

        // 自动滚动到底部，显示最新日志
        scrollToBottom();

        // State Pattern: 根据新状态刷新 UI
        updateButtonStates(learningPlan.getState());

        // 如果完成了，弹窗提示
        if (learningPlan.getState() instanceof CompletedState) {
            JOptionPane.showMessageDialog(this, "恭喜！学习计划已圆满完成！");
        }
    }

    /**
     * 核心加分点：通过状态模式控制 UI 行为
     * 将状态逻辑与界面逻辑解耦
     */
    private void updateButtonStates(LearningState state) {
        if (learningPlan == null) {
            setBtnEnabled(true, false, false, false);
            return;
        }

        // 判断 state 的具体类型 (instanceof)
        if (state instanceof NotStartedState) {
            setBtnEnabled(true, true, false, false);
            startBtn.setText("开始学习");
        } else if (state instanceof InProgressState) {
            setBtnEnabled(false, false, true, true);
        } else if (state instanceof PausedState) {
            setBtnEnabled(false, true, false, false);
            startBtn.setText("继续学习");
        } else if (state instanceof CompletedState) {
            setBtnEnabled(true, false, false, false); // 完成后允许重新生成新计划
            startBtn.setText("开始学习");
        }
    }

    private void setBtnEnabled(boolean gen, boolean start, boolean pause, boolean complete) {
        generateBtn.setEnabled(gen);
        startBtn.setEnabled(start);
        pauseBtn.setEnabled(pause);
        completeBtn.setEnabled(complete);
    }

    /**
     * 自动滚动到底部，显示最新内容
     */
    private void scrollToBottom() {
        SwingUtilities.invokeLater(() -> {
            // 将光标移动到文档末尾
            outputArea.setCaretPosition(outputArea.getDocument().getLength());
            // 确保滚动条滚动到底部
            outputArea.scrollRectToVisible(outputArea.getVisibleRect());
        });
    }
}
