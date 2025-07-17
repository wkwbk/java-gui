package MainFrom.RootFrom;

import java.awt.*;
import java.util.List;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import MainFrom.MyPanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import method.CustomerMethod;
import model.Customer;

public class CustomerManagePanel extends MyPanel {

    private static final Logger logger = LoggerFactory.getLogger(CustomerManagePanel.class);

    public CustomerMethod customerMethod = new CustomerMethod();
    private List<Customer> customers;

    public CustomerManagePanel() {
        getCustomers();

        // 添加选择框
        gbc.gridx = 0;
        gbc.gridy = 0;
        JButton loadCustomersButton = new JButton("加载信息");
        loadCustomersButton.addActionListener(e -> refreshCustomerTableData());
        topPanel.add(loadCustomersButton, gbc);

        gbc.gridx = 1;
        JButton updateCustomersButton = new JButton("修改信息");
        updateCustomersButton.addActionListener(e -> updateCustomersWindow());
        topPanel.add(updateCustomersButton, gbc);

        // 创建表格
        table.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{
                        "客户ID", "账号", "密码", "电话"
                }
        ));
    }

    /**
     * 获取客户信息
     */
    public void getCustomers() {
        Runnable loadTask = () -> {
            System.out.println("🙌🙌🙌异步加载客户信息🙌🙌🙌");
            try {
                // 获取客户信息
                customers = customerMethod.query();
            } catch (Exception e) {
                logger.error("查询客户信息失败", e);
                // 弹出提示框
                JOptionPane.showMessageDialog(this, "加载客户信息失败，请重试！");
            }
            SwingUtilities.invokeLater(this::refreshCustomerTableData);
            System.out.println("🎉🎉🎉异步加载客户信息完成🎉🎉🎉");
        };
        new Thread(loadTask).start();
    }

    /**
     * 刷新客户表格数据
     */
    public void refreshCustomerTableData() {
        Runnable loadTask = () -> {
            System.out.println("异步刷新客户表格数据");
            // 填充表格数据
            DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
            // 清空表格数据
            while (tableModel.getRowCount() > 0) {
                tableModel.removeRow(tableModel.getRowCount() - 1);
            }
            for (Customer customer : customers) {
                Vector<Object> v = new Vector<>();
                v.add(customer.getCustomerID());
                v.add(customer.getCustomerName());
                v.add(customer.getCustomerPassword());
                v.add(customer.getCustomerPhone());
                tableModel.addRow(v);
            }
            System.out.println("异步刷新客户表格数据完成");
        };
        new Thread(loadTask).start();
    }

    /**
     * 修改客户信息窗口
     */
    private void updateCustomersWindow() {
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
        panel.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1;
        JTextField idField = new JTextField();
        idField.setPreferredSize(new Dimension(200, 30));
        panel.add(idField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("修改项:"), gbc);
        gbc.gridx = 1;
        JComboBox<String> comboBox = new JComboBox<>(new String[]{"账号", "密码", "电话"});
        comboBox.setPreferredSize(new Dimension(200, 30));
        panel.add(comboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("修改内容:"), gbc);
        gbc.gridx = 1;
        JTextField contentField = new JTextField();
        contentField.setPreferredSize(new Dimension(200, 30));
        panel.add(contentField, gbc);

        // 对话框
        int result = JOptionPane.showConfirmDialog(
                this,
                panel,
                "修改信息",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        // 根据用户的选择处理结果
        if (result == JOptionPane.OK_OPTION) {
            updateCustomers(idField.getText(), (String) comboBox.getSelectedItem(), contentField.getText());
        }
    }

    /**
     * 修改客户信息
     */
    public void updateCustomers(String idField, String comboBoxItem, String contentField) {
        int customerID = 0;

        // 将id转换为整数
        for (int i = 0; i < idField.length(); i++) {
            customerID += (int) ((idField.charAt(i) - 48) * Math.pow(10, (idField.length() - i) - 1));
        }
        System.out.println(customerID);

        // 查找对应的客户并进行更新
        for (Customer customer : customers) {
            if (customer.getCustomerID() == customerID) {
                CustomerMethod g = new CustomerMethod();
                if (comboBoxItem != null) {
                    switch (comboBoxItem) {
                        case "账号":
                            if (contentField.length() < 3) {
                                JOptionPane.showMessageDialog(this, "账号过短");
                                return;
                            }
                            customer.setCustomerName(contentField);
                            try {
                                g.updateCustomer2(customer);
                                JOptionPane.showMessageDialog(this, "修改成功");
                            } catch (Exception e) {
                                logger.error("更新客户信息失败，客户id: {}", customer.getCustomerID(), e);
                                JOptionPane.showMessageDialog(this, "修改失败，请稍后重试！");
                            }
                            break;
                        case "密码":
                            if (contentField.length() < 5) {
                                JOptionPane.showMessageDialog(this, "密码过短");
                                return;
                            }
                            customer.setCustomerPassword(contentField);
                            try {
                                g.updateCustomer2(customer);
                                JOptionPane.showMessageDialog(this, "修改成功");
                            } catch (Exception e) {
                                logger.error("更新客户信息失败，客户id: {}", customer.getCustomerID(), e);
                                JOptionPane.showMessageDialog(this, "修改失败，请稍后重试！");
                            }
                            break;
                        case "电话":
                            if (contentField.length() != 11) {
                                JOptionPane.showMessageDialog(this, "请输入正确的手机号");
                                return;
                            }
                            customer.seamyCall(contentField);
                            try {
                                g.updateCustomer2(customer);
                                JOptionPane.showMessageDialog(this, "修改成功");
                            } catch (Exception e) {
                                logger.error("更新客户信息失败，客户id: {}", customer.getCustomerID(), e);
                                JOptionPane.showMessageDialog(this, "修改失败，请稍后重试！");
                            }
                            break;
                        default:
                            JOptionPane.showMessageDialog(this, "系统错误");
                    }
                }
                refreshCustomerTableData();
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "id错误");
    }
}
