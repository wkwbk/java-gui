package MainFrom;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @projectName: HotelManagement
 * @package: MainFrom
 * @className: MyFrame
 * @author: LI SIR
 * @description: MyFrame 类是酒店管理系统的主框架类，负责设置窗口的基本属性，
 *               包括菜单栏、任务栏和内容面板。提供统一的窗口布局和主题切换功能，
 *               并支持动态状态更新和页面跳转。
 * @date: 2024/12/23 23:24
 * @version: 1.0
 */
public class MyFrame extends JFrame {
    public JPanel contentPanel;     // 内容面板
    public JPanel taskBar;          // 任务栏面板
    public JTabbedPane tabbedPane;  // 选项卡面板
    public JMenuBar menuBar;        // 菜单栏
    public JButton exitButton;      // 退出登录按钮
    public JLabel statusLabel;      // 动态状态标签

    public MyFrame() {
        // 设置窗口属性
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 设置窗口关闭时操作为退出
        setSize(1080, 780); // 设置窗口的大小
        setLocationRelativeTo(null); // 设置窗口居中显示
        setIconImage(new ImageIcon("src\\img\\icon.png").getImage()); // 设置窗口图标

        // 创建菜单栏
        menuBar = new JMenuBar();
        // 创建文件菜单
        JMenu fileMenu = new JMenu("File");
        // 创建菜单项
        JMenuItem newMenuItem = new JMenuItem("New");
        JMenuItem openMenuItem = new JMenuItem("Open...");
        JMenuItem saveAsMenuItem = new JMenuItem("Save As...");
        JMenuItem closeMenuItem = new JMenuItem("Close");
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        // 设置快捷键
        newMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));  // Ctrl + N
        openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));  // Ctrl + O
        saveAsMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK)); // Ctrl + S
        closeMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_DOWN_MASK));  // Ctrl + W
        exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));  // Ctrl + Q
        // 为菜单项添加事件监听
        openMenuItem.addActionListener(e -> new JFileChooser().showOpenDialog(this));
        saveAsMenuItem.addActionListener(e -> new JFileChooser().showSaveDialog(this));
        exitMenuItem.addActionListener(e -> exitApplication());
        // 将菜单项添加到文件菜单
        fileMenu.add(newMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(saveAsMenuItem);
        fileMenu.addSeparator();  // 添加分隔线
        fileMenu.add(closeMenuItem);
        fileMenu.addSeparator();  // 添加分隔线
        fileMenu.add(exitMenuItem);
        // 将文件菜单添加到菜单栏
        menuBar.add(fileMenu);
        // 将菜单栏添加到窗口
        setJMenuBar(menuBar);

        // 创建任务栏
        taskBar = new JPanel();
        taskBar.setLayout(new BorderLayout());
        taskBar.setPreferredSize(new Dimension(getWidth(), 50));

        // 创建分隔线并添加到任务栏
        JPanel separatorPanel = new JPanel();
        separatorPanel.setLayout(new BorderLayout());
        separatorPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // 设置上下内边距
        separatorPanel.add(new JSeparator());
        taskBar.add(separatorPanel, BorderLayout.NORTH);

        // 创建退出登录按钮并添加到任务栏
        exitButton = new JButton("退出登录");
        exitButton.addActionListener(e -> {
            // 关闭当前窗口
            dispose();
            // 打开登录窗口
            SwingUtilities.invokeLater(() -> new Login().setVisible(true));
        });
        taskBar.add(exitButton, BorderLayout.EAST);

        // 创建动态状态标签并添加到任务栏
        statusLabel = new JLabel("状态：正在加载...");
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER); // 居中显示
        // 使用 Timer 定时更新状态标签
        Timer timer = new Timer(1000, e -> {
            // 获取当前时间
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedTime = now.format(formatter);
            // 更新状态标签
            statusLabel.setText("当前时间：" + formattedTime);
        });
        timer.start();
        taskBar.add(statusLabel, BorderLayout.CENTER);

        // 获取主题选择下拉框并添加到任务栏
        taskBar.add(getThemeComboBox(), BorderLayout.WEST);

        // 创建选项卡面板
        tabbedPane = new JTabbedPane(JTabbedPane.TOP);

        // 创建内容面板
        contentPanel = (JPanel) getContentPane();
        contentPanel.setLayout(new BorderLayout()); // 设置布局管理器
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // 内边距

        // 将任务栏添加到内容面板
        contentPanel.add(taskBar, BorderLayout.SOUTH);
        contentPanel.add(tabbedPane, BorderLayout.CENTER);
    }

    /**
     * 获取主题选择下拉框
     *
     * @return JComboBox<String>
     */
    public JComboBox<String> getThemeComboBox() {
        String[] themes = {"Dark", "Light"};
        JComboBox<String> themeComboBox = new JComboBox<>(themes);
        themeComboBox.setSelectedIndex(0); // 默认选择暗黑主题
        themeComboBox.addItemListener(e -> {
            // 切换主题
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String selectedTheme = (String) e.getItem();
                try {
                    if ("Dark".equals(selectedTheme)) {
                        UIManager.setLookAndFeel(new FlatDarkLaf());
                    } else if ("Light".equals(selectedTheme)) {
                        UIManager.setLookAndFeel(new FlatLightLaf());
                    }
                    SwingUtilities.updateComponentTreeUI(this); // 更新界面
                } catch (UnsupportedLookAndFeelException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        return themeComboBox;
    }

    /**
     * 退出应用程序的功能
     */
    private void exitApplication() {
        int confirm = JOptionPane.showConfirmDialog(this, "您确定要退出吗？", "退出", JOptionPane.OK_CANCEL_OPTION);
        if (confirm == JOptionPane.OK_OPTION) {
            System.exit(0);  // 退出程序
        }
    }

    /**
     * 向选项卡面板中添加一个新选项卡，包含指定的标题、图标、内容组件和提示信息。
     *
     * @param title     选项卡的标题，显示在选项卡上
     * @param icon      选项卡标题旁显示的图标（可以为 null）
     * @param component 选项卡被选中时显示的组件（例如 JPanel）
     * @param tooltip   鼠标悬停在选项卡上时显示的提示文本（可以为 null）
     */
    public void addTab(String title, Icon icon, Component component, String tooltip) {
        tabbedPane.addTab(title, icon, component, tooltip);
    }
}
