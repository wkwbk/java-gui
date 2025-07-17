package MainFrom.RootFrom;

import java.awt.*;
import java.util.List;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import MainFrom.MyPanel;
import method.ContactMethod;
import method.RoomMethod;
import method.OrderMethod;
import model.Contact;
import model.Order;

import java.sql.SQLException;

public class OrderManagePanel extends MyPanel {

    public OrderMethod orderMethod = new OrderMethod(); //获取顾客表的操作方法
    public ContactMethod contactMethod = new ContactMethod(); //获取顾客表的操作方法
    public RoomMethod roomMethod = new RoomMethod();
    Contact contact = new Contact();
    List<Order> orders;

    /**
     * 创建订单管理面板
     */
    public OrderManagePanel() {
        // 加载订单按钮
        gbc.gridx = 0;
        gbc.gridy = 0;
        JButton loadOrderButton = new JButton("加载订单");
        loadOrderButton.addActionListener(e -> getOrders());
        topPanel.add(loadOrderButton, gbc);

        gbc.gridx = 1;
        JButton queryOrderButton = new JButton("查询订单");
        queryOrderButton.addActionListener(e -> queryOrderWindow());
        topPanel.add(queryOrderButton, gbc);

        gbc.gridx = 2;
        JButton deleteOrderButton = new JButton("删除订单");
        deleteOrderButton.addActionListener(e -> deleteOrderWindow());
        topPanel.add(deleteOrderButton, gbc);

        table.setModel(new DefaultTableModel(
                new Object[][]{
                },
                new String[]{
                        "订单号", "房间号", "客户ID", "房间类型", "开始时间", "结束时间"
                }
        ));

        getOrders();
    }

    /**
     * 获取订单信息
     */
    public void getOrders() {
        // 异步加载房间数据
        Runnable loadTask = () -> {
            System.out.println("🙌🙌🙌异步加载订单信息🙌🙌🙌");
            try {
                orders = orderMethod.query();
            } catch (Exception e) {
                SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(this, "加载订单信息失败，请重试！"));
                throw new RuntimeException(e);
            }
            SwingUtilities.invokeLater(this::refreshOrderTableData); // 在事件调度线程中更新表格数据
            System.out.println("🎉🎉🎉异步加载订单信息完成🎉🎉🎉");
        };
        new Thread(loadTask).start(); // 启动后台线程
    }

    /**
     * 刷新订单表格数据
     */
    public void refreshOrderTableData() {
        Runnable refreshTask = () -> {
            System.out.println("异步刷新订单表格数据");
            // 表格显示信息
            DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
            // 清空表格数据
            while (tableModel.getRowCount() > 0) {
                tableModel.removeRow(tableModel.getRowCount() - 1);
            }
            for (Order order : orders) {
                try {
                    contact = contactMethod.getContact(order.getOrderNumber());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                Vector<Object> v = new Vector<>();
                v.add(order.getOrderNumber());
                v.add(contact.getHomeNumber());
                v.add(order.getCustomerID());
                v.add(order.getRoomType());
                v.add(order.getStartTime());
                v.add(contact.getEndTime());
                tableModel.addRow(v);
            }
            System.out.println("异步刷新订单表格数据完成");
        };
        new Thread(refreshTask).start();
    }

    /**
     * 查询订单窗口
     */
    private void queryOrderWindow() {
        // 创建主面板，使用 GridBagLayout 来设置灵活的布局
        JPanel panel = new JPanel(new GridBagLayout());
        // 在 panel 面板的外层添加了一个复合边框
        panel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder("填写信息"),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5)
                )
        );

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // 设置组件间距
        gbc.anchor = GridBagConstraints.WEST; // 设置组件的对齐方式（左对齐）

        // 房间类型
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("查询方式:"), gbc);
        gbc.gridx = 1;
        JComboBox<String> typeComboBox = new JComboBox<>(new String[]{"订单号查询", "ID查询"});
        typeComboBox.setPreferredSize(new Dimension(200, 30));
        panel.add(typeComboBox, gbc);

        // 价格
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("输入内容:"), gbc);
        gbc.gridx = 1;
        JTextField priceField = new JTextField();
        priceField.setPreferredSize(new Dimension(200, 30));
        panel.add(priceField, gbc);

        // 对话框
        int result = JOptionPane.showConfirmDialog(
                this,
                panel,
                "查询订单",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        // 根据用户的选择处理结果
        if (result == JOptionPane.OK_OPTION) {
            queryOrder(typeComboBox.getSelectedIndex(), priceField.getText());
        }
    }

    /**
     * 查询订单
     */
    public void queryOrder(int result, String Str) {
        Runnable queryTask = () -> {
            if (result == 0) {
                //表格显示信息
                DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
                //清空
                while (tableModel.getRowCount() > 0) {
                    tableModel.removeRow(tableModel.getRowCount() - 1);
                }
                for (Order order : orders) {
                    if (order.getOrderNumber().equals(Str)) {

                        try {
                            contact = contactMethod.getContact(order.getOrderNumber());
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                        Vector<Object> v = new Vector<>();
                        v.add(order.getOrderNumber());
                        v.add(contact.getHomeNumber());
                        v.add(order.getCustomerID());
                        v.add(order.getRoomType());
                        v.add(order.getStartTime());
                        v.add(contact.getEndTime());
                        tableModel.addRow(v);
                    }
                }
            } else {
                int orderID = 0;
                for (int i = 0; i < Str.length(); i++) {
                    orderID += (int) ((Str.charAt(i) - 48) * Math.pow(10, (Str.length() - i) - 1));
                }
                //表格显示信息
                DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
                //清空
                while (tableModel.getRowCount() > 0) {
                    tableModel.removeRow(tableModel.getRowCount() - 1);
                }
                for (Order order : orders) {
                    if (order.getCustomerID() == orderID) {
                        Contact Con = new Contact();
                        try {
                            Con = contactMethod.getContact(order.getOrderNumber());
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                        Vector<Object> v = new Vector<Object>();
                        v.add(order.getOrderNumber());
                        v.add(Con.getHomeNumber());
                        v.add(order.getCustomerID());
                        v.add(order.getRoomType());
                        v.add(order.getStartTime());
                        v.add(Con.getEndTime());
                        tableModel.addRow(v);
                    }
                }
            }
        };
        new Thread(queryTask).start(); // 启动后台线程
    }

    /**
     * 删除订单窗口
     */
    private void deleteOrderWindow() {
        // 创建主面板，使用 GridBagLayout 来设置灵活的布局
        JPanel panel = new JPanel(new GridBagLayout());
        // 在 panel 面板的外层添加了一个复合边框
        panel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder("填写信息"),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5)
                )
        );

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // 设置组件间距
        gbc.anchor = GridBagConstraints.WEST; // 设置组件的对齐方式（左对齐）

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("订单号:"), gbc);
        gbc.gridx = 1;
        JTextField orderNumberField = new JTextField();
        orderNumberField.setPreferredSize(new Dimension(200, 30));
        panel.add(orderNumberField, gbc);

        // 对话框
        int result = JOptionPane.showConfirmDialog(
                this,
                panel,
                "删除订单",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        // 根据用户的选择处理结果
        if (result == JOptionPane.OK_OPTION) {
            deleteOrder(orderNumberField.getText());
        }
    }

    /**
     * 删除订单
     */
    public void deleteOrder(String orderNumber) {
        Runnable deleteTask = () -> {
            for (Order order : orders) {
                if (order.getOrderNumber().equals(orderNumber)) {
                    try {
                        roomMethod.updateCustomer(contactMethod.getContact(order.getOrderNumber()).getHomeNumber());
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                    try {
                        orderMethod.deleteCustomer(orderNumber);
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    try {
                        contactMethod.deleteContact(orderNumber);
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    //表格显示信息
                    DefaultTableModel dtm = (DefaultTableModel) table.getModel();
                    //清空
                    while (dtm.getRowCount() > 0) {
                        dtm.removeRow(dtm.getRowCount() - 1);
                    }
                    List<Order> m = null;
                    try {
                        m = orderMethod.query();
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                    for (Order order1 : m) {
                        Contact Con = new Contact();
                        try {
                            Con = contactMethod.getContact(order1.getOrderNumber());
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                        Vector<Object> v = new Vector<>();
                        v.add(order1.getOrderNumber());
                        v.add(Con.getHomeNumber());
                        v.add(order1.getCustomerID());
                        v.add(order1.getRoomType());
                        v.add(order1.getStartTime());
                        v.add(Con.getEndTime());
                        dtm.addRow(v);
                    }
                    return;
                }
            }
            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(this, "请输入正确订单号"));
        };
        new Thread(deleteTask).start(); // 启动后台线程
    }
}
