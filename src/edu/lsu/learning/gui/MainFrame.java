package edu.lsu.learning.gui;

import edu.lsu.learning.factory.JavaLearningPlanFactory;
import edu.lsu.learning.observer.PlanObserver;
import edu.lsu.learning.plan.LearningPlan;
import edu.lsu.learning.state.*;
import edu.lsu.learning.strategy.*;
import edu.lsu.learning.template.DefaultLearningPlanGenerator;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * 主界面类 - 同时也是 Observer (观察者)
 * 负责展示界面，并根据 LearningPlan 的通知自动更新显示
 * (原生风格版本)
 */
public class MainFrame extends JFrame implements PlanObserver {

    private JTextField nameField;
    private JTextField goalField;
    private JComboBox<String> strategyBox;
    private JTextArea outputArea;
    private JButton generateBtn;
    private JButton startBtn;
    private JButton pauseBtn;
    private JButton completeBtn;

    private LearningPlan learningPlan;

    public MainFrame() {
        setTitle("个性化学习计划生成与执行管理系统");
        setSize(800, 610);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 使用系统默认外观，让界面看起来像原生 Windows/Mac 程序
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        JPanel rootPanel = new JPanel(new BorderLayout());
        rootPanel.setBorder(new EmptyBorder(15, 20, 15, 20));
        add(rootPanel);

        // 顶部标题 - 居中显示
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBorder(new EmptyBorder(0, 0, 15, 0));
        JLabel titleLabel = new JLabel("个性化学习计划生成与执行管理系统");
        titleLabel.setFont(new Font("宋体", Font.BOLD, 22));
        titlePanel.add(titleLabel);
        rootPanel.add(titlePanel, BorderLayout.NORTH);

        // 中间主要区域
        JPanel centerPanel = new JPanel(new BorderLayout(0, 12));
        centerPanel.add(createInputPanel(), BorderLayout.NORTH);
        centerPanel.add(createButtonPanel(), BorderLayout.CENTER);
        centerPanel.add(createOutputPanel(), BorderLayout.SOUTH);

        rootPanel.add(centerPanel, BorderLayout.CENTER);

        // 绑定事件
        generateBtn.addActionListener(e -> generatePlan());
        startBtn.addActionListener(e -> performAction("start"));
        pauseBtn.addActionListener(e -> performAction("pause"));
        completeBtn.addActionListener(e -> performAction("complete"));

        updateButtonStates(null);
    }

    /**
     * 观察者模式核心方法：响应状态变化
     */
    @Override
    public void update(LearningPlan plan) {
        if (plan == null) return;

        // 1. 刷新日志显示
        String latestLog = plan.getLatestLog();
        if (!latestLog.isEmpty()) {
            String currentText = outputArea.getText();
            if (!currentText.trim().endsWith(latestLog.trim())) {
                outputArea.append(latestLog + "\n");
                scrollToBottom();
            }
        }

        // 2. 刷新按钮状态
        updateButtonStates(plan.getState());
    }

    private void generatePlan() {
        String name = nameField.getText().trim();
        String goal = goalField.getText().trim();

        if (name.isEmpty() || goal.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请输入完整信息！", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Factory Pattern
        JavaLearningPlanFactory factory = new JavaLearningPlanFactory();
        learningPlan = factory.createLearningPlan(name, goal);

        // Observer Pattern: 注册自己
        learningPlan.attach(this);

        // Strategy Pattern
        LearningPlanStrategy strategy;
        int idx = strategyBox.getSelectedIndex();
        if (idx == 1) strategy = new AdvancedLearningStrategy();
        else if (idx == 2) strategy = new SprintLearningStrategy();
        else strategy = new BasicLearningStrategy();

        // Template Method Pattern
        DefaultLearningPlanGenerator generator = new DefaultLearningPlanGenerator();
        outputArea.setText("");
        outputArea.append(">>> 系统正在初始化...\n");
        outputArea.append(">>> 正在识别计划类型: " + learningPlan.getClass().getSimpleName() + "\n");

        String planContent = generator.generate(name, goal, strategy);
        learningPlan.setPlanContent(planContent); // 触发 update

        outputArea.append(planContent);
        outputArea.append("\n>>> 计划已生成，请点击\"开始学习\"按钮开始执行\n\n");

        updateButtonStates(learningPlan.getState());
    }

    private void performAction(String action) {
        if (learningPlan == null) return;

        switch (action) {
            case "start": learningPlan.start(); break;
            case "pause": learningPlan.pause(); break;
            case "complete":
                learningPlan.complete();
                if (learningPlan.getState() instanceof CompletedState) {
                    JOptionPane.showMessageDialog(this, "恭喜！学习计划已圆满完成！");
                }
                break;
        }
    }

    private void updateButtonStates(LearningState state) {
        if (learningPlan == null) {
            setBtnEnabled(true, false, false, false);
            return;
        }

        if (state instanceof NotStartedState) {
            setBtnEnabled(true, true, false, false);
            startBtn.setText("开始学习");
        } else if (state instanceof InProgressState) {
            setBtnEnabled(false, false, true, true);
        } else if (state instanceof PausedState) {
            setBtnEnabled(false, true, false, false);
            startBtn.setText("继续学习");
        } else if (state instanceof CompletedState) {
            setBtnEnabled(true, false, false, false);
            startBtn.setText("开始学习");
        }
    }

    private JPanel createInputPanel() {
        JPanel inputPanel = new JPanel(new GridBagLayout());
        javax.swing.border.TitledBorder border = BorderFactory.createTitledBorder("参数配置");
        border.setTitleFont(border.getTitleFont().deriveFont(Font.BOLD));
        inputPanel.setBorder(border);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 12, 8, 12);
        gbc.anchor = GridBagConstraints.WEST;

        // 学习者姓名
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        inputPanel.add(new JLabel("学习者姓名："), gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        nameField = new JTextField(20);
        inputPanel.add(nameField, gbc);

        // 学习目标
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        inputPanel.add(new JLabel("学习目标："), gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        goalField = new JTextField(20);
        inputPanel.add(goalField, gbc);

        // 策略选择
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        inputPanel.add(new JLabel("策略选择："), gbc);

        gbc.gridx = 1; gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        strategyBox = new JComboBox<>(new String[]{
            "基础型策略 (稳扎稳打)",
            "提升型策略 (深度钻研)",
            "冲刺型策略 (考前突击)"
        });
        inputPanel.add(strategyBox, gbc);

        return inputPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 8));
        buttonPanel.setBorder(new EmptyBorder(5, 0, 5, 0));

        generateBtn = new JButton("生成学习计划");
        startBtn = new JButton("开始/继续学习");
        pauseBtn = new JButton("暂停学习");
        completeBtn = new JButton("完成学习");

        buttonPanel.add(generateBtn);
        buttonPanel.add(startBtn);
        buttonPanel.add(pauseBtn);
        buttonPanel.add(completeBtn);

        return buttonPanel;
    }

    private JScrollPane createOutputPanel() {
        outputArea = new JTextArea(15, 60);
        outputArea.setEditable(false);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        outputArea.setMargin(new Insets(8, 8, 8, 8));

        JScrollPane scrollPane = new JScrollPane(outputArea);
        javax.swing.border.TitledBorder border = BorderFactory.createTitledBorder("系统运行日志与计划详情");
        border.setTitleFont(border.getTitleFont().deriveFont(Font.BOLD));
        scrollPane.setBorder(border);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        return scrollPane;
    }

    private void setBtnEnabled(boolean gen, boolean start, boolean pause, boolean complete) {
        generateBtn.setEnabled(gen);
        startBtn.setEnabled(start);
        pauseBtn.setEnabled(pause);
        completeBtn.setEnabled(complete);
    }

    private void scrollToBottom() {
        SwingUtilities.invokeLater(() -> {
            try {
                outputArea.setCaretPosition(outputArea.getDocument().getLength());
            } catch (Exception e) {}
        });
    }
}
