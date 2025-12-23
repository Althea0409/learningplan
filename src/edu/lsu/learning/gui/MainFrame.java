/**
 * 个性化学习计划生成与执行管理系统 - 主界面
 * 
 * 本类负责构建和管理整个应用程序的图形用户界面，采用简约科技风格设计
 * 实现了学习计划的生成、状态管理和执行控制等功能
 * 
 * @author LearningPlan System
 * @version 1.0
 */
package edu.lsu.learning.gui;

import edu.lsu.learning.factory.JavaLearningPlanFactory;
import edu.lsu.learning.plan.LearningPlan;
import edu.lsu.learning.state.*;
import edu.lsu.learning.strategy.*;
import edu.lsu.learning.template.DefaultLearningPlanGenerator;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class MainFrame extends JFrame {

    // 科技风格配色方案
    private static final Color BG_DARK = new Color(20, 25, 35);
    private static final Color BG_PANEL = new Color(28, 35, 48);
    private static final Color ACCENT_CYAN = new Color(0, 200, 255);
    private static final Color ACCENT_PURPLE = new Color(138, 43, 226);
    private static final Color TEXT_PRIMARY = new Color(220, 220, 220);
    private static final Color TEXT_SECONDARY = new Color(160, 170, 180);
    private static final Color BORDER_COLOR = new Color(50, 60, 75);

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
        setTitle("个性化学习计划生成与执行管理系统 - 设计模式期末作业");
        setSize(900, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(BG_DARK);
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout(0, 0));

        JPanel titlePanel = createTitlePanel();
        add(titlePanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout(15, 15));
        centerPanel.setBackground(BG_DARK);
        centerPanel.setBorder(new EmptyBorder(20, 30, 20, 30));
        add(centerPanel, BorderLayout.CENTER);

        centerPanel.add(createInputPanel(), BorderLayout.NORTH);
        centerPanel.add(createButtonPanel(), BorderLayout.CENTER);
        centerPanel.add(createOutputPanel(), BorderLayout.SOUTH);

        generateBtn.addActionListener(e -> generatePlan());
        startBtn.addActionListener(e -> performAction("start"));
        pauseBtn.addActionListener(e -> performAction("pause"));
        completeBtn.addActionListener(e -> performAction("complete"));

        updateButtonStates(null);
    }

    private JPanel createTitlePanel() {
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(BG_DARK);
        titlePanel.setBorder(new EmptyBorder(25, 20, 25, 20));
        
        JLabel titleLabel = new JLabel("个性化学习计划生成与执行管理系统", JLabel.CENTER);
        titleLabel.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 26));
        titleLabel.setForeground(ACCENT_CYAN);
        
        JLabel subtitleLabel = new JLabel("Learning Plan Management System", JLabel.CENTER);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitleLabel.setForeground(TEXT_SECONDARY);
        subtitleLabel.setBorder(new EmptyBorder(5, 0, 0, 0));
        
        JPanel textPanel = new JPanel(new BorderLayout());
        textPanel.setBackground(BG_DARK);
        textPanel.add(titleLabel, BorderLayout.CENTER);
        textPanel.add(subtitleLabel, BorderLayout.SOUTH);
        titlePanel.add(textPanel, BorderLayout.CENTER);
        
        return titlePanel;
    }

    private JPanel createInputPanel() {
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(BG_PANEL);
        inputPanel.setBorder(createTechBorder("参数配置"));
        inputPanel.setPreferredSize(new Dimension(0, 180));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 20, 15, 20);
        gbc.anchor = GridBagConstraints.WEST;
        
        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(createLabel("学习者姓名："), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        nameField = createTextField();
        inputPanel.add(nameField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        inputPanel.add(createLabel("学习目标："), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        goalField = createTextField();
        inputPanel.add(goalField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        inputPanel.add(createLabel("策略选择："), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        strategyBox = createComboBox();
        inputPanel.add(strategyBox, gbc);
        
        return inputPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        buttonPanel.setBackground(BG_DARK);
        buttonPanel.setBorder(new EmptyBorder(10, 0, 10, 0));
        
        generateBtn = createTechButton("生成学习计划", ACCENT_CYAN);
        startBtn = createTechButton("开始/继续学习", new Color(50, 205, 50));
        pauseBtn = createTechButton("暂停学习", new Color(255, 165, 0));
        completeBtn = createTechButton("完成学习", ACCENT_PURPLE);
        
        buttonPanel.add(generateBtn);
        buttonPanel.add(startBtn);
        buttonPanel.add(pauseBtn);
        buttonPanel.add(completeBtn);
        
        return buttonPanel;
    }

    private JScrollPane createOutputPanel() {
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(createChineseMonospaceFont(13));
        outputArea.setBackground(new Color(15, 20, 28));
        outputArea.setForeground(new Color(0, 255, 150));
        outputArea.setCaretColor(ACCENT_CYAN);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        outputArea.setBorder(new EmptyBorder(15, 15, 15, 15));
        
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBackground(BG_PANEL);
        scrollPane.setBorder(createTechBorder("系统运行日志与计划详情"));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(0, 280));
        
        JScrollBar vScrollBar = scrollPane.getVerticalScrollBar();
        vScrollBar.setUnitIncrement(16);
        vScrollBar.setBackground(BG_PANEL);
        vScrollBar.setForeground(ACCENT_CYAN);
        
        return scrollPane;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
        label.setForeground(TEXT_PRIMARY);
        return label;
    }

    private JTextField createTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 13));
        field.setBackground(new Color(35, 42, 55));
        field.setForeground(TEXT_PRIMARY);
        field.setCaretColor(ACCENT_CYAN);
        field.setBorder(new CompoundBorder(
            new LineBorder(BORDER_COLOR, 1),
            new EmptyBorder(8, 12, 8, 12)
        ));
        return field;
    }

    private JComboBox<String> createComboBox() {
        JComboBox<String> combo = new JComboBox<>(new String[]{
            "基础型策略 (稳扎稳打)",
            "提升型策略 (深度钻研)",
            "冲刺型策略 (考前突击)"
        });
        combo.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 13));
        combo.setBackground(new Color(35, 42, 55));
        combo.setForeground(TEXT_PRIMARY);
        combo.setBorder(new CompoundBorder(
            new LineBorder(BORDER_COLOR, 1),
            new EmptyBorder(6, 10, 6, 10)
        ));
        return combo;
    }

    private JButton createTechButton(String text, Color color) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (getModel().isPressed()) {
                    g2.setColor(color.darker());
                } else if (getModel().isRollover()) {
                    g2.setColor(color.brighter());
                } else {
                    g2.setColor(color);
                }
                
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose();
                
                super.paintComponent(g);
            }
        };
        
        btn.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 13));
        btn.setForeground(Color.WHITE);
        btn.setBackground(color);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setPreferredSize(new Dimension(150, 40));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        return btn;
    }

    private Border createTechBorder(String title) {
        TitledBorder border = BorderFactory.createTitledBorder(
            BorderFactory.createCompoundBorder(
                new LineBorder(BORDER_COLOR, 1),
                new EmptyBorder(10, 10, 10, 10)
            ),
            title
        );
        border.setTitleFont(new Font("Microsoft YaHei UI", Font.BOLD, 13));
        border.setTitleColor(ACCENT_CYAN);
        border.setTitlePosition(TitledBorder.TOP);
        border.setTitleJustification(TitledBorder.LEFT);
        return border;
    }

    /**
     * 创建支持中文的等宽字体，优先尝试等宽中文字体
     */
    private Font createChineseMonospaceFont(int size) {
        String[] fontNames = {
            "Microsoft YaHei Mono",
            "NSimSun",
            "SimSun",
            "Microsoft YaHei UI"
        };
        
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] availableFonts = ge.getAvailableFontFamilyNames();
        
        for (String fontName : fontNames) {
            for (String available : availableFonts) {
                if (available.equals(fontName)) {
                    System.out.println("[GUI] 输出区域使用字体: " + fontName + " (大小: " + size + ")");
                    return new Font(fontName, Font.PLAIN, size);
                }
            }
        }
        
        System.out.println("[GUI] 警告：未找到合适的中文字体，使用默认等宽字体（可能不支持中文）");
        return new Font(Font.MONOSPACED, Font.PLAIN, size);
    }

    /**
     * 生成学习计划
     * 使用工厂模式创建学习计划对象，策略模式选择学习策略，模板方法模式生成计划内容
     */
    private void generatePlan() {
        System.out.println("[GUI] 用户点击生成学习计划按钮");
        
        String name = nameField.getText().trim();
        String goal = goalField.getText().trim();

        if (name.isEmpty() || goal.isEmpty()) {
            System.out.println("[GUI] 输入验证失败：姓名或目标为空");
            JOptionPane.showMessageDialog(this, "请输入完整信息！", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }

        System.out.println("[GUI] 开始生成学习计划 - 姓名: " + name + ", 目标: " + goal);

        // Factory Pattern: 创建 Plan
        JavaLearningPlanFactory factory = new JavaLearningPlanFactory();
        learningPlan = factory.createLearningPlan(name, goal);
        System.out.println("[GUI] 使用工厂模式创建学习计划对象");

        // Strategy Pattern: 选择策略
        LearningPlanStrategy strategy;
        int idx = strategyBox.getSelectedIndex();
        if (idx == 1) {
            strategy = new AdvancedLearningStrategy();
            System.out.println("[GUI] 选择策略：提升型策略");
        } else if (idx == 2) {
            strategy = new SprintLearningStrategy();
            System.out.println("[GUI] 选择策略：冲刺型策略");
        } else {
            strategy = new BasicLearningStrategy();
            System.out.println("[GUI] 选择策略：基础型策略");
        }

        // Template Method Pattern: 生成流程
        DefaultLearningPlanGenerator generator = new DefaultLearningPlanGenerator();
        outputArea.setText("");
        outputArea.append(">>> 系统正在初始化...\n");
        outputArea.append(">>> 正在分析学习目标和当前水平...\n");
        outputArea.append(">>> 正在生成个性化学习计划...\n\n");

        String planContent = generator.generate(name, goal, strategy);
        learningPlan.setPlanContent(planContent);

        outputArea.append(planContent);
        outputArea.append("\n>>> 计划已生成，请点击\"开始学习\"按钮开始执行\n\n");

        System.out.println("[GUI] 学习计划生成完成");
        scrollToBottom();
        updateButtonStates(learningPlan.getState());
    }

    private void performAction(String action) {
        if (learningPlan == null) {
            System.out.println("[GUI] 警告：尝试执行动作但学习计划未生成");
            return;
        }

        System.out.println("[GUI] 执行动作: " + action);

        switch (action) {
            case "start":
                learningPlan.start();
                System.out.println("[GUI] 学习计划状态：开始/继续学习");
                break;
            case "pause":
                learningPlan.pause();
                System.out.println("[GUI] 学习计划状态：暂停学习");
                break;
            case "complete":
                learningPlan.complete();
                System.out.println("[GUI] 学习计划状态：完成学习");
                break;
        }

        outputArea.append(learningPlan.getLatestLog() + "\n");
        scrollToBottom();

        // State Pattern: 根据新状态刷新 UI
        updateButtonStates(learningPlan.getState());

        if (learningPlan.getState() instanceof CompletedState) {
            System.out.println("[GUI] 学习计划已完成，显示完成提示");
            JOptionPane.showMessageDialog(this, 
                "恭喜！学习计划已圆满完成！", 
                "完成提示", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * 通过状态模式控制 UI 行为
     * 将状态逻辑与界面逻辑解耦，根据当前学习计划状态动态更新按钮的可用性
     */
    private void updateButtonStates(LearningState state) {
        System.out.println("[GUI] 更新按钮状态，当前状态: " + 
            (state != null ? state.getClass().getSimpleName() : "null"));
        
        if (learningPlan == null) {
            setBtnEnabled(true, false, false, false);
            return;
        }

        if (state instanceof NotStartedState) {
            setBtnEnabled(true, true, false, false);
            startBtn.setText("开始学习");
            System.out.println("[GUI] 按钮状态：未开始 - 可生成、可开始");
        } else if (state instanceof InProgressState) {
            setBtnEnabled(false, false, true, true);
            System.out.println("[GUI] 按钮状态：进行中 - 可暂停、可完成");
        } else if (state instanceof PausedState) {
            setBtnEnabled(false, true, false, false);
            startBtn.setText("继续学习");
            System.out.println("[GUI] 按钮状态：已暂停 - 可继续");
        } else if (state instanceof CompletedState) {
            setBtnEnabled(true, false, false, false);
            startBtn.setText("开始学习");
            System.out.println("[GUI] 按钮状态：已完成 - 可重新生成");
        }
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
                outputArea.scrollRectToVisible(outputArea.getVisibleRect());
            } catch (Exception e) {
                System.err.println("[GUI] 滚动到底部时发生错误: " + e.getMessage());
            }
        });
    }
}
