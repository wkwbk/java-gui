package MainFrom.UserFrom;

import javax.swing.*;

import method.CustomerMethod;
import model.Customer;

import java.awt.*;
import java.util.List;

public class UserCenterPanel extends JPanel {

    public final JTextField nameField, passwordField, callField;
    public final JLabel name, password, call;
    public CustomerMethod customerMethod = new CustomerMethod();//获取顾客表的操作方法
    public List<Customer> customers;//返回顾客信息到List中
    public int userID;

    public UserCenterPanel(int userID) {
        this.userID = userID;
        getUser(userID);
        getUser();
        // 设置布局
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10, 10, 10, 10); // 设置按钮之间的间距
        gbc.anchor = GridBagConstraints.WEST; // 设置组件的对齐方式（左对齐）
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel nameLabel = new JLabel("账号：");
        add(nameLabel, gbc);

        gbc.gridy = 1;
        JLabel passwordLabel = new JLabel("密码：");
        add(passwordLabel, gbc);

        gbc.gridy = 2;
        JLabel callLabel = new JLabel("电话：");
        add(callLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        name = new JLabel("");
        add(name, gbc);

        gbc.gridy = 1;
        password = new JLabel("");
        add(password, gbc);

        gbc.gridy = 2;
        call = new JLabel("");
        add(call, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        nameField = new JTextField();
        add(nameField, gbc);
        nameField.setColumns(10);

        gbc.gridy = 1;
        passwordField = new JTextField();
        add(passwordField, gbc);
        passwordField.setColumns(10);

        gbc.gridy = 2;
        callField = new JTextField();
        add(callField, gbc);
        callField.setColumns(10);

        // 创建按钮并设置操作
        gbc.gridx = 3;
        gbc.gridy = 0;
        JButton btnNewButton = new JButton("修改");
        btnNewButton.addActionListener(event -> updateName());
        add(btnNewButton, gbc);

        gbc.gridy = 1;
        JButton button = new JButton("修改");
        button.addActionListener(event -> updatePassword());
        add(button, gbc);

        gbc.gridy = 2;
        JButton button_1 = new JButton("修改");
        button_1.addActionListener(event -> updateCall());
        add(button_1, gbc);
    }

    /**
     * 获取用户信息
     */
    public void getUser() {
        new Thread(() -> {
            try {
                customers = customerMethod.query();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public void getUser(int userID) {
        new Thread(() -> {
            System.out.println("🙌🙌🙌异步加载用户信息🙌🙌🙌");
            Customer customer;
            try {
                customer = customerMethod.get(userID);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            SwingUtilities.invokeLater(() -> {
                name.setText(customer.getCustomerName());
                password.setText(customer.getCustomerPassword());
                call.setText(customer.getCustomerPhone());
            });
            System.out.println("🎉🎉🎉异步加载用户信息完成🎉🎉🎉");
        }).start();
    }

    /**
     * 更新用户昵称
     */
    public void updateName() {
        String Str = nameField.getText();
        for (Customer customer : customers) {
            if (customer.getCustomerName().equals(Str)) {
                JOptionPane.showMessageDialog(this, "昵称被占用");
                return;
            }
        }
        if (Str.length() < 3) {
            JOptionPane.showMessageDialog(this, "昵称太短");
            return;
        }
        if (Str.length() > 20) {
            JOptionPane.showMessageDialog(this, "昵称太长");
            return;
        }
        Customer cos1;
        try {
            cos1 = customerMethod.get(userID);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        cos1.setCustomerName(Str);
        name.setText(cos1.getCustomerName());
        try {
            customerMethod.updateCustomer2(cos1);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        JOptionPane.showMessageDialog(this, "修改成功");
    }

    /**
     * 更新用户密码
     */
    public void updatePassword() {
        String Str = passwordField.getText();
        if (Str.length() < 5) {
            JOptionPane.showMessageDialog(this, "密码太短");
            return;
        }
        if (Str.length() > 20) {
            JOptionPane.showMessageDialog(this, "密码太长");
            return;
        }
        Customer cos1;
        try {
            cos1 = customerMethod.get(userID);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        cos1.setCustomerPassword(Str);
        password.setText(cos1.getCustomerPassword());
        try {
            customerMethod.updateCustomer2(cos1);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        JOptionPane.showMessageDialog(this, "修改成功");
    }

    /**
     * 更新用户电话
     */
    public void updateCall() {
        String Str = callField.getText();
        if (Str.length() != 11) {
            JOptionPane.showMessageDialog(this, "请输入正确手机号");
            return;
        }
        Customer cos1;
        try {
            cos1 = customerMethod.get(userID);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        cos1.seamyCall(Str);
        call.setText(cos1.getCustomerPhone());
        try {
            customerMethod.updateCustomer2(cos1);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        JOptionPane.showMessageDialog(this, "修改成功");
    }
}
