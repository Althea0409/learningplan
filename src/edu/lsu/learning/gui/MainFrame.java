package edu.lsu.learning.gui;

import edu.lsu.learning.factory.JavaLearningPlanFactory;
import edu.lsu.learning.observer.PlanObserver;
import edu.lsu.learning.plan.LearningPlan;
import edu.lsu.learning.state.*;
import edu.lsu.learning.strategy.*;
import edu.lsu.learning.template.DefaultLearningPlanGenerator;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

/**
 * 主界面类 - 同时也是 Observer (观察者)
 * 科技简约风格，颜色参考深蓝(#051555)与亮蓝(#184EE4)
 */
public class MainFrame extends JFrame implements PlanObserver {

    // 配色方案
    private static final Color COLOR_PRIMARY = new Color(0x051555);      // 深蓝背景
    private static final Color COLOR_ACCENT = new Color(0x184EE4);       // 亮蓝强调
    private static final Color COLOR_CARD_BG = new Color(0xEEF2FB);      // 淡蓝卡片
    private static final Color COLOR_TEXT = new Color(0x0B1A3C);         // 深色文字
    private static final Color COLOR_BORDER = new Color(0xEEF2FB);       // 边框颜色(与卡片同色)
    private static final Color COLOR_BTN_SUCCESS = new Color(0x00BF63);  // 开始/继续：绿
    private static final Color COLOR_BTN_WARNING = new Color(0xFF751F);  // 暂停：橙
    private static final Color COLOR_BTN_PURPLE = new Color(0xFF3A3A);   // 完成：红

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
        setSize(900, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 背景色
        getContentPane().setBackground(COLOR_PRIMARY);

        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        JPanel rootPanel = new JPanel(new BorderLayout(0, 16));
        rootPanel.setBackground(COLOR_PRIMARY);
        rootPanel.setBorder(new EmptyBorder(20, 26, 20, 26));
        add(rootPanel);

        // 顶部标题
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        titlePanel.setBackground(COLOR_PRIMARY);
        JLabel titleLabel = new JLabel("个性化学习计划生成与执行管理系统");
        titleLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 26));
        titleLabel.setForeground(new Color(0xE8EDFF));
        titlePanel.add(titleLabel);
        rootPanel.add(titlePanel, BorderLayout.NORTH);

        // 中间卡片区域
        JPanel centerPanel = new JPanel(new BorderLayout(0, 14));
        centerPanel.setBackground(COLOR_PRIMARY);
        centerPanel.add(createCardPanel(createInputPanel()), BorderLayout.NORTH);
        centerPanel.add(createCardPanel(createButtonPanel()), BorderLayout.CENTER);
        centerPanel.add(createCardPanel(createOutputPanel()), BorderLayout.SOUTH);

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
        inputPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 12, 10, 12);
        gbc.anchor = GridBagConstraints.WEST;

        // 学习者姓名
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        inputPanel.add(createLabel("学习者姓名："), gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        nameField = createTextField();
        inputPanel.add(nameField, gbc);

        // 学习目标
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        inputPanel.add(createLabel("学习目标："), gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        goalField = createTextField();
        inputPanel.add(goalField, gbc);

        // 策略选择
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        inputPanel.add(createLabel("策略选择："), gbc);

        gbc.gridx = 1; gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        strategyBox = createComboBox();
        inputPanel.add(strategyBox, gbc);

        return inputPanel;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(COLOR_TEXT);
        label.setFont(new Font("Microsoft YaHei", Font.BOLD, 13));
        return label;
    }

    private JTextField createTextField() {
        JTextField field = new JTextField(20);
        field.setBackground(Color.WHITE);
        field.setForeground(COLOR_TEXT);
        field.setCaretColor(COLOR_ACCENT);
        field.setFont(new Font("Microsoft YaHei", Font.PLAIN, 13));
        field.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(COLOR_BORDER, 1, true),
            new EmptyBorder(6, 10, 6, 10)
        ));
        return field;
    }

    private JComboBox<String> createComboBox() {
        JComboBox<String> combo = new JComboBox<>(new String[]{
            "基础型策略 (稳扎稳打)",
            "提升型策略 (深度钻研)",
            "冲刺型策略 (考前突击)"
        });
        combo.setBackground(Color.WHITE);
        combo.setForeground(COLOR_TEXT);
        combo.setFont(new Font("Microsoft YaHei", Font.PLAIN, 13));
        combo.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(COLOR_BORDER, 1, true),
            new EmptyBorder(4, 8, 4, 8)
        ));
        return combo;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 12));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(new EmptyBorder(6, 0, 6, 0));

        generateBtn = createTechButton("生成学习计划", COLOR_ACCENT);
        startBtn = createTechButton("开始/继续学习", COLOR_BTN_SUCCESS);
        pauseBtn = createTechButton("暂停学习", COLOR_BTN_WARNING);
        completeBtn = createTechButton("完成学习", COLOR_BTN_PURPLE);

        buttonPanel.add(generateBtn);
        buttonPanel.add(startBtn);
        buttonPanel.add(pauseBtn);
        buttonPanel.add(completeBtn);

        return buttonPanel;
    }

    private JButton createTechButton(String text, Color color) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                Color base = color;
                if (!isEnabled()) {
                    base = new Color(color.getRed(), color.getGreen(), color.getBlue(), 110);
                } else if (getModel().isPressed()) {
                    base = color.darker();
                } else if (getModel().isRollover()) {
                    base = color.brighter();
                }

                g2.setColor(base);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose();

                super.paintComponent(g);
            }
        };

        btn.setFont(new Font("Microsoft YaHei", Font.BOLD, 13));
        btn.setForeground(Color.WHITE);
        btn.setBackground(color);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setPreferredSize(new Dimension(140, 38));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return btn;
    }

    private JScrollPane createOutputPanel() {
        outputArea = new JTextArea(15, 60);
        outputArea.setEditable(false);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        outputArea.setBackground(Color.WHITE);
        outputArea.setForeground(COLOR_TEXT);
        outputArea.setCaretColor(COLOR_ACCENT);
        outputArea.setFont(new Font("Microsoft YaHei", Font.PLAIN, 13));
        outputArea.setMargin(new Insets(12, 12, 12, 12));

        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBackground(COLOR_CARD_BG);
        scrollPane.getViewport().setOpaque(true);
        scrollPane.getViewport().setBackground(COLOR_CARD_BG);

        TitledBorder border = BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(COLOR_ACCENT, 1),
            "系统运行日志与计划详情"
        );
        border.setTitleFont(new Font("Microsoft YaHei", Font.BOLD, 13));
        border.setTitleColor(COLOR_ACCENT);
        border.setTitlePosition(TitledBorder.TOP);
        scrollPane.setBorder(new CompoundBorder(border, new EmptyBorder(8, 8, 8, 8)));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        // 滚动条背景也统一为卡片色，避免灰色突兀
        JScrollBar vScrollBar = scrollPane.getVerticalScrollBar();
        if (vScrollBar != null) {
            vScrollBar.setOpaque(true);
            vScrollBar.setBackground(COLOR_CARD_BG);
        }
        JScrollBar hScrollBar = scrollPane.getHorizontalScrollBar();
        if (hScrollBar != null) {
            hScrollBar.setOpaque(true);
            hScrollBar.setBackground(COLOR_CARD_BG);
        }

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

    private Font createChineseMonospaceFont(int size) {
        String[] fontNames = {
            "Microsoft YaHei Mono",
            "NSimSun",
            "SimSun",
            "Microsoft YaHei"
        };

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] availableFonts = ge.getAvailableFontFamilyNames();

        for (String fontName : fontNames) {
            for (String available : availableFonts) {
                if (available.equals(fontName)) {
                    return new Font(fontName, Font.PLAIN, size);
                }
            }
        }

        return new Font(Font.MONOSPACED, Font.PLAIN, size);
    }

    /**
     * 卡片容器，提供浅色背景和圆角边框
     */
    private JPanel createCardPanel(JComponent content) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(COLOR_CARD_BG);
        card.setBorder(new CompoundBorder(
            new LineBorder(COLOR_BORDER, 1, true),
            new EmptyBorder(14, 16, 14, 16)
        ));
        card.add(content, BorderLayout.CENTER);
        return card;
    }
}
