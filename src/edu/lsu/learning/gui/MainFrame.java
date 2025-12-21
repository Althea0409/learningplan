package edu.lsu.learning.gui;

import edu.lsu.learning.factory.JavaLearningPlanFactory;
import edu.lsu.learning.plan.LearningPlan;
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

    private LearningPlan learningPlan;

    public MainFrame() {
        setTitle("个性化学习计划生成与执行管理系统");
        setSize(650, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));

        // ===== 顶部标题 =====
        JLabel titleLabel = new JLabel("个性化学习计划生成与执行管理系统", JLabel.CENTER);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 20));
        titleLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        add(titleLabel, BorderLayout.NORTH);

        // ===== 中部主面板 =====
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBorder(new EmptyBorder(10, 20, 10, 20));
        add(centerPanel, BorderLayout.CENTER);

        // ===== 输入区 =====
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("学习者信息"));

        inputPanel.add(new JLabel("学习者姓名："));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("学习目标："));
        goalField = new JTextField();
        inputPanel.add(goalField);

        inputPanel.add(new JLabel("学习策略："));
        strategyBox = new JComboBox<>(new String[]{
                "基础型学习策略",
                "提升型学习策略",
                "冲刺型学习策略"
        });
        inputPanel.add(strategyBox);

        centerPanel.add(inputPanel, BorderLayout.NORTH);

        // ===== 按钮区 =====
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));

        JButton generateBtn = new JButton("生成学习计划");
        JButton startBtn = new JButton("开始学习");
        JButton pauseBtn = new JButton("暂停学习");
        JButton completeBtn = new JButton("完成学习");

        buttonPanel.add(generateBtn);
        buttonPanel.add(startBtn);
        buttonPanel.add(pauseBtn);
        buttonPanel.add(completeBtn);

        centerPanel.add(buttonPanel, BorderLayout.CENTER);

        // ===== 输出区 =====
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("系统输出"));

        centerPanel.add(scrollPane, BorderLayout.SOUTH);

        // ===== 事件绑定 =====
        generateBtn.addActionListener(e -> generatePlan());
        startBtn.addActionListener(e -> changeState("start"));
        pauseBtn.addActionListener(e -> changeState("pause"));
        completeBtn.addActionListener(e -> changeState("complete"));
    }

    // ===== 业务逻辑调用 =====
    private void generatePlan() {
        String name = nameField.getText();
        String goal = goalField.getText();

        if (name.isEmpty() || goal.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请填写完整信息！");
            return;
        }

        JavaLearningPlanFactory factory = new JavaLearningPlanFactory();
        learningPlan = factory.createLearningPlan(name, goal);

        LearningPlanStrategy strategy;
        switch (strategyBox.getSelectedIndex()) {
            case 1:
                strategy = new AdvancedLearningStrategy();
                break;
            case 2:
                strategy = new SprintLearningStrategy();
                break;
            default:
                strategy = new BasicLearningStrategy();
        }

        DefaultLearningPlanGenerator generator = new DefaultLearningPlanGenerator();
        outputArea.append("=== 开始生成学习计划 ===\n");
        generator.generate(name, goal, strategy);
        outputArea.append("=== 学习计划生成完毕 ===\n\n");
    }

    private void changeState(String action) {
        if (learningPlan == null) {
            JOptionPane.showMessageDialog(this, "请先生成学习计划！");
            return;
        }

        switch (action) {
            case "start":
                learningPlan.start();
                outputArea.append("状态变更：开始学习\n");
                break;
            case "pause":
                learningPlan.pause();
                outputArea.append("状态变更：暂停学习\n");
                break;
            case "complete":
                learningPlan.complete();
                outputArea.append("状态变更：完成学习\n");
                break;
        }
        outputArea.append("\n");
    }
}
