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
 * 【主界面类 - GUI入口】
 * 
 * 功能说明：
 * AI智能个性化学习计划生成系统的主界面。
 * 采用黑金极客风主题，提供简洁美观的用户交互界面。
 * 
 * 应用的设计模式：
 * 1. 单例模式 - ResourceCenter（资源中心）
 * 2. 工厂方法模式 - TaskFactory（任务工厂）
 * 3. 建造者模式 - PlanBuilder（计划构建）
 * 4. 装饰者模式 - PlanDecorator（VIP装饰）
 * 
 * @author 系统设计
 * @version 1.0
 */
public class AppWindow extends JFrame {

    // 界面颜色配置
    private static final Color BG_COLOR = new Color(30, 30, 30);       // 全局深色背景
    private static final Color PANEL_COLOR = new Color(45, 45, 45);    // 侧边栏背景
    private static final Color TEXT_COLOR = new Color(220, 220, 220);  // 主文字颜色
    private static final Color ACCENT_COLOR = new Color(230, 180, 34); // 金色强调色

    // GUI组件
    private JTextArea displayArea;
    private JComboBox<String> modeBox;

    /**
     * 构造函数
     */
    public AppWindow() {
        setTitle("AI智能 · 个性化学习计划生成系统");
        setSize(950, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // 屏幕居中
        setLayout(new BorderLayout());

        initUI();
        
        // 记录系统启动日志
        System.out.println("[日志] 系统启动完成，所有设计模式已初始化");
    }

    /**
     * 初始化用户界面
     */
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
        clearBtn.addActionListener(e -> {
            displayArea.setText(getWelcomeMessage());
            System.out.println("[日志] 显示区域已清空");
        });
        controlPanel.add(clearBtn);

        add(controlPanel, BorderLayout.WEST);

        // 3. 右侧展示区域
        displayArea = new JTextArea();
        displayArea.setFont(new Font("Microsoft YaHei", Font.PLAIN, 15));
        displayArea.setBackground(BG_COLOR);
        displayArea.setForeground(TEXT_COLOR);
        displayArea.setEditable(false);
        displayArea.setMargin(new Insets(20, 20, 20, 20));
        displayArea.setText(getWelcomeMessage());

        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * 获取欢迎消息
     */
    private String getWelcomeMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append("╔══════════════════════════════════════════════════════════╗\n");
        sb.append("║     AI智能 · 个性化学习计划生成系统                      ║\n");
        sb.append("╚══════════════════════════════════════════════════════════╝\n\n");
        sb.append(">>> 系统初始化完成...\n");
        sb.append(">>> 资源中心 (Singleton) 已加载题库...\n");
        sb.append(">>> 等待指令...\n\n");
        sb.append("【已应用的设计模式】\n");
        sb.append("1. 单例模式 (Singleton) - 资源中心\n");
        sb.append("2. 工厂方法模式 (Factory Method) - 任务创建\n");
        sb.append("3. 建造者模式 (Builder) - 计划构建\n");
        sb.append("4. 装饰者模式 (Decorator) - VIP装饰\n");
        return sb.toString();
    }

    /**
     * 生成学习计划（整合多种设计模式）
     * 
     * 设计模式应用：
     * - 建造者模式：通过Director和PlanBuilder构建学习计划
     * - 工厂方法模式：通过TaskFactory创建不同类型的任务
     * - 单例模式：通过ResourceCenter获取资源
     * - 装饰者模式：为VIP用户装饰展示效果
     */
    private void generatePlan(String subject, String mode) {
        System.out.println("[日志] 开始生成学习计划 - 科目: " + subject + ", 模式: " + mode);
        
        displayArea.append("\n" + repeatString("=", 60) + "\n");
        displayArea.append(String.format(">>> 正在为 [%s] 生成计划...\n", subject));
        displayArea.append(">>> 核心引擎启动 (Builder Pattern)...\n");

        // 建造者模式：创建Director和Builder
        Director director = new Director();
        PlanBuilder builder;
        boolean isVip = false;

        // 根据模式选择不同的建造者
        if (mode.contains("基础")) {
            builder = new BasicsBuilder();
            System.out.println("[日志] 选择基础巩固模式建造者");
        } else {
            builder = new RushBuilder();
            isVip = true;
            System.out.println("[日志] 选择高分冲刺模式建造者");
        }

        // 建造者模式：构建学习计划（内部使用工厂方法模式和单例模式）
        LearningPlan plan = director.construct(builder);
        System.out.println("[日志] 学习计划构建完成");

        // 装饰者模式：为VIP用户装饰展示效果
        String result;
        if (isVip) {
            displayArea.append(">>> 检测到 VIP 权限，正在装饰视图 (Decorator)...\n");
            result = new PlanDecorator(plan).getVipReport();
            System.out.println("[日志] VIP装饰已应用");
        } else {
            result = plan.getReport();
        }

        displayArea.append(result);
        
        // 自动滚动到底部
        scrollToBottom();
        System.out.println("[日志] 学习计划生成完成");
    }

    // ========== UI 辅助方法 ==========

    /**
     * 添加标签到面板
     */
    private void addLabel(JPanel panel, String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
        label.setForeground(new Color(180, 180, 180));
        panel.add(label);
    }

    /**
     * 设置文本框样式
     */
    private void styleTextField(JTextField tf) {
        tf.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
        tf.setBackground(new Color(60, 60, 60));
        tf.setForeground(Color.WHITE);
        tf.setBorder(BorderFactory.createLineBorder(new Color(80, 80, 80)));
        tf.setCaretColor(ACCENT_COLOR);
    }

    /**
     * 设置下拉框样式
     */
    private void styleComboBox(JComboBox<String> box) {
        box.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
        box.setBackground(new Color(60, 60, 60));
        box.setForeground(Color.WHITE);
    }

    /**
     * 创建样式化按钮
     */
    private JButton createStyledButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Microsoft YaHei", Font.BOLD, 15));
        btn.setForeground(fg);
        btn.setBackground(bg);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // 鼠标悬停变色效果
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { 
                btn.setBackground(bg.brighter()); 
            }
            public void mouseExited(MouseEvent e) { 
                btn.setBackground(bg); 
            }
        });
        return btn;
    }

    /**
     * 滚动到底部
     */
    private void scrollToBottom() {
        SwingUtilities.invokeLater(() -> {
            displayArea.setCaretPosition(displayArea.getDocument().getLength());
        });
    }

    /**
     * 重复字符串（兼容Java 8）
     * @param str 要重复的字符串
     * @param count 重复次数
     * @return 重复后的字符串
     */
    private String repeatString(String str, int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(str);
        }
        return sb.toString();
    }

    // ========== 程序入口 ==========

    /**
     * 程序入口
     */
    public static void main(String[] args) {
        // 开启文字抗锯齿
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");

        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new AppWindow().setVisible(true);
        });
    }
}
