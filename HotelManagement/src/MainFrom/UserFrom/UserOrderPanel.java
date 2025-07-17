package MainFrom.UserFrom;

import java.util.List;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import MainFrom.MyPanel;
import method.ContactMethod;
import method.OrderMethod;
import model.Contact;
import model.Order;

public class UserOrderPanel extends MyPanel {

    public OrderMethod orderMethod = new OrderMethod();
    public List<Order> orders;
    public int userID;

    public UserOrderPanel(int userID) {
        this.userID = userID;
        // 加载订单按钮
        gbc.gridx = 0;
        gbc.gridy = 0;
        JButton loadRoomButton = new JButton("加载订单");
        loadRoomButton.addActionListener(e -> getOrders());
        topPanel.add(loadRoomButton, gbc);

        table.setModel(new DefaultTableModel(
                new Object[][]{
                },
                new String[]{
                        "订单号", "id", "房间号", "房间类型", "开始时间", "结束时间"
                }
        ));

        getOrders();
    }

    /**
     * 加载订单
     */
    public void getOrders() {
        // 异步加载房间数据
        new Thread(() -> {
            System.out.println("🙌🙌🙌异步加载订单信息🙌🙌🙌");
            try {
                orders = orderMethod.query();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            SwingUtilities.invokeLater(this::refreshOrderTableData); // 在事件调度线程中更新表格数据
            System.out.println("🎉🎉🎉异步加载订单信息完成🎉🎉🎉");
        }).start();
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
                if (order.getCustomerID() == userID) {
                    Contact Con;
                    try {
                        //获取顾客表的操作方法
                        ContactMethod contactMethod = new ContactMethod();
                        Con = contactMethod.getContact(order.getOrderNumber());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    Vector<Object> v = new Vector<>();
                    v.add(order.getOrderNumber());
                    v.add(order.getCustomerID());
                    v.add(Con.getHomeNumber());
                    v.add(order.getRoomType());
                    v.add(order.getStartTime());
                    v.add(Con.getEndTime());
                    tableModel.addRow(v);
                }
            }
            System.out.println("异步刷新订单表格数据完成");
        };
        new Thread(refreshTask).start();
    }
}
