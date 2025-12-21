package com.exam.gui;

import com.exam.builder.*;
import com.exam.decorator.PlanDecorator;
import com.exam.model.LearningPlan;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 图形化界面入口 (GUI)
 * 采用黑金极客风主题
 */
public class AppWindow extends JFrame {

    // 界面颜色配置
    private static final Color BG_COLOR = new Color(30, 30, 30);       // 全局深色背景
    private static final Color PANEL_COLOR = new Color(45, 45, 45);    // 侧边栏背景
    private static final Color TEXT_COLOR = new Color(220, 220, 220);  // 主文字颜色
    private static final Color ACCENT_COLOR = new Color(230, 180, 34); // 金色强调色

    private JTextArea displayArea;
    private JComboBox<String> modeBox;

    public AppWindow() {
        setTitle("AI智能 · 个性化学习计划生成系统");
        setSize(950, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // 屏幕居中
        setLayout(new BorderLayout());

        initUI();
    }

    private void initUI() {
        // 1. 顶部标题栏
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(BG_COLOR);
        headerPanel.setBorder(new EmptyBorder(25, 20, 25, 20));
        JLabel titleLabel = new JLabel("Design Pattern Learning System");
        titleLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 26));
        titleLabel.setForeground(ACCENT_COLOR);
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);

        // 2. 左侧控制面板
        JPanel controlPanel = new JPanel();
        controlPanel.setBackground(PANEL_COLOR);
        controlPanel.setLayout(new GridLayout(10, 1, 15, 15));
        controlPanel.setBorder(new EmptyBorder(30, 20, 30, 20));
        controlPanel.setPreferredSize(new Dimension(280, 0));

        // 控件：科目输入
        addLabel(controlPanel, "📚 目标科目:");
        JTextField subjectField = new JTextField("Java设计模式");
        styleTextField(subjectField);
        controlPanel.add(subjectField);

        // 控件：模式选择
        addLabel(controlPanel, "⚙️ 生成模式:");
        modeBox = new JComboBox<>(new String[]{"基础巩固模式", "高分冲刺模式 (VIP)"});
        styleComboBox(modeBox);
        controlPanel.add(modeBox);

        // 占位空标签
        controlPanel.add(new JLabel(""));

        // 按钮：生成
        JButton generateBtn = createStyledButton("🚀 生成学习计划", ACCENT_COLOR, Color.BLACK);
        generateBtn.addActionListener(e -> {
            String subject = subjectField.getText();
            String mode = (String) modeBox.getSelectedItem();
            generatePlan(subject, mode);
        });
        controlPanel.add(generateBtn);

        // 按钮：清空
        JButton clearBtn = createStyledButton("🗑️ 清空屏幕", new Color(80, 80, 80), Color.WHITE);
        clearBtn.addActionListener(e -> displayArea.setText(""));
        controlPanel.add(clearBtn);

        add(controlPanel, BorderLayout.WEST);

        // 3. 右侧展示区域
        displayArea = new JTextArea();
        displayArea.setFont(new Font("Microsoft YaHei", Font.PLAIN, 15));
        displayArea.setBackground(BG_COLOR);
        displayArea.setForeground(TEXT_COLOR);
        displayArea.setEditable(false);
        displayArea.setMargin(new Insets(20, 20, 20, 20));
        displayArea.setText(">>> 系统初始化完成...\n>>> 资源中心 (Singleton) 已加载题库...\n>>> 等待指令...\n");

        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);
    }

    // --- 核心业务逻辑 ---
    private void generatePlan(String subject, String mode) {
        displayArea.append("\n----------------------------------------\n");
        displayArea.append(String.format(">>> 正在为 [%s] 生成计划...\n", subject));
        displayArea.append(">>> 核心引擎启动 (Builder Pattern)...\n");

        Director director = new Director();
        PlanBuilder builder;
        boolean isVip = false;

        // 策略选择
        if (mode.contains("基础")) {
            builder = new BasicsBuilder();
        } else {
            builder = new RushBuilder();
            isVip = true;
        }

        // 构建过程 (Builder + Factory + Singleton)
        LearningPlan plan = director.construct(builder);

        // 结果展示 (Decorator)
        String result;
        if (isVip) {
            displayArea.append(">>> 检测到 VIP 权限，正在装饰视图 (Decorator)...\n");
            result = new PlanDecorator(plan).getVipReport();
        } else {
            result = plan.getReport();
        }

        displayArea.append(result);
        // 自动滚动到底部
        displayArea.setCaretPosition(displayArea.getDocument().getLength());
    }

    // --- UI 辅助方法 ---
    private void addLabel(JPanel panel, String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
        label.setForeground(new Color(180, 180, 180));
        panel.add(label);
    }

    private void styleTextField(JTextField tf) {
        tf.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
        tf.setBackground(new Color(60, 60, 60));
        tf.setForeground(Color.WHITE);
        tf.setBorder(BorderFactory.createLineBorder(new Color(80, 80, 80)));
        tf.setCaretColor(ACCENT_COLOR);
    }

    private void styleComboBox(JComboBox<String> box) {
        box.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
        box.setBackground(new Color(60, 60, 60));
        box.setForeground(Color.WHITE); // 注意：Swing组合框在某些系统下颜色样式可能受限
    }

    private JButton createStyledButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Microsoft YaHei", Font.BOLD, 15));
        btn.setForeground(fg);
        btn.setBackground(bg);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // 简单的鼠标悬停变色效果
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(bg.brighter()); }
            public void mouseExited(MouseEvent e) { btn.setBackground(bg); }
        });
        return btn;
    }

    // 程序入口
    public static void main(String[] args) {
        // 尝试开启文字抗锯齿
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");

        SwingUtilities.invokeLater(() -> {
            new AppWindow().setVisible(true);
        });
    }
}
