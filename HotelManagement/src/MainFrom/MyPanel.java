package MainFrom;

import javax.swing.*;
import java.awt.*;

/**
 * @projectName: HotelManagement
 * @package: MainFrom.RootWindow
 * @className: MyPanel
 * @author: LI SIR
 * @description: TODO
 * @date: 2024/12/23 12:57
 * @version: 1.0
 */
public class MyPanel extends JPanel {
    public JTable table;        // 中面板表格
    public JPanel topPanel;     // 上面板
    public JPanel centerPanel;  // 中面板
    public GridBagConstraints gbc;

    public MyPanel() {
        // 设置布局管理器
        setLayout(new BorderLayout());
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 10, 5); // 设置按钮之间的间距

        // 创建面板
        topPanel = new JPanel(new GridBagLayout());
        centerPanel = new JPanel(new BorderLayout());

        // 创建表格
        table = new JTable(); // 创建表格
        table.setDefaultEditor(Object.class, null); // 设置表格不可编辑
        table.setShowGrid(true); // 显示网格线
        JScrollPane scrollPane = new JScrollPane(table); // 创建滚动面板
        centerPanel.add(scrollPane, BorderLayout.CENTER); // 添加滚动面板

        // 添加面板
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
    }
}
