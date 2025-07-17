package MainFrom;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import com.formdev.flatlaf.FlatDarkLaf;

import MainFrom.RootFrom.RootMain;
import MainFrom.UserFrom.UserMain;
import method.CustomerMethod;
import model.Customer;

/**
 * @projectName: HotelManagement
 * @package: MainFrom
 * @className: Login
 * @author: LI SIR
 * @description: Login 类是酒店管理系统的登录界面，负责用户登录、账号注册、密码修改等操作。
 *               用户通过输入账号和密码登录系统，管理员可访问管理界面，普通用户可访问用户界面。
 *               提供用户友好的界面和必要的校验逻辑，确保操作安全性。
 * @date: 2024/12/23 23:24
 * @version: 1.0
 */
public class Login extends JFrame {

    static {
        // 设置外观
        FlatDarkLaf.setup();
        // 标签之间显示分隔线
        UIManager.put("TabbedPane.showTabSeparators", true);
        // 获取当前默认字体
        Font defaultFont = UIManager.getFont("defaultFont");
        // 修改字体大小为 13，保留字体和样式
        UIManager.put("defaultFont", defaultFont.deriveFont(13f));
        // 设置部件直角
        UIManager.put("Button.arc", 0);
        UIManager.put("Component.arc", 0);
        UIManager.put("CheckBox.arc", 0);
        UIManager.put("ProgressBar.arc", 0);
        // 滚动条设置
        UIManager.put("ScrollBar.thumbInsets", new Insets(2, 2, 2, 2));
    }

    private final JTextField username;                                      // 用户名框，用于输入用户名
    private final JPasswordField password;                                  // 密码框，用于输入密码
    private final JButton loginButton;                                      // 登录按钮
    private final JButton changePasswordButton;                             // 修改密码按钮
    private final JButton registerButton;                                   // 注册账号按钮
    private final CustomerMethod customerMethod = new CustomerMethod();     // 用于存储 CustomerMethod 实例
    private List<Customer> customers;                                       // 用于存储用户列表

    /**
     * 程序的主入口，启动应用程序
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Login frame = new Login(); // 创建登录窗口实例
                frame.setVisible(true); // 显示窗口
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * 创建框架，初始化登录界面
     */
    public Login() {
        // 设置窗口属性
        setResizable(false); // 禁止窗口大小调整
        setSize(340, 460); // 设置窗口的大小
        setLocationRelativeTo(null); // 设置窗口居中显示
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 设置关闭操作

        // 获取内容面板
        JPanel contentPane = (JPanel) getContentPane();
        setContentPane(contentPane); // 将面板添加到窗口
        contentPane.setLayout(null); // 取消布局管理器

        // 创建标题标签
        JLabel titleLabel = new JLabel("226 国 际 酒 店", SwingConstants.CENTER);
        titleLabel.setFont(new Font("宋体", Font.BOLD, 18));
        titleLabel.setBounds(0, 40, getWidth(), 30);
        contentPane.add(titleLabel);

        // 创建账号标签
        JLabel usernameLabel = new JLabel("账号:");
        usernameLabel.setBounds(25, 100, 100, 30);
        contentPane.add(usernameLabel);

        // 创建密码标签
        JLabel passwordLabel = new JLabel("密码:");
        passwordLabel.setBounds(25, 150, 100, 30);
        contentPane.add(passwordLabel);

        // 创建账号输入框
        username = new JTextField();
        username.setBounds(70, 100, 210, 30);
        contentPane.add(username);

        // 创建密码输入框
        password = new JPasswordField();
        password.setBounds(70, 150, 210, 30);
        contentPane.add(password);

        // 创建登录按钮
        loginButton = new JButton("登录");
        loginButton.setBounds(70, 200, 210, 30);
        loginButton.setEnabled(false); // 初始时禁用按钮
        loginButton.addActionListener(e -> EventQueue.invokeLater(this::performLogin));
        contentPane.add(loginButton);

        // 创建修改密码按钮
        changePasswordButton = new JButton("修改密码");
        changePasswordButton.setBounds(70, 250, 100, 30);
        changePasswordButton.setEnabled(false); // 初始时禁用按钮
        changePasswordButton.addActionListener(e -> EventQueue.invokeLater(this::change));
        contentPane.add(changePasswordButton);

        // 创建注册账号按钮
        registerButton = new JButton("注册账号");
        registerButton.setBounds(180, 250, 100, 30);
        registerButton.setEnabled(false); // 初始时禁用按钮
        registerButton.addActionListener(e -> EventQueue.invokeLater(this::register));
        contentPane.add(registerButton);

        // 启动后台任务来查询数据
        loadCustomerData();
    }

    /**
     * 加载顾客数据的方法
     */
    private void loadCustomerData() {
        new Thread(() -> {
            System.out.println("加载顾客数据中...");
            try {
                // 获取所有用户数据
                customers = customerMethod.query();

                // 在事件调度线程（EDT）上更新 UI
                SwingUtilities.invokeLater(() -> {
                    loginButton.setEnabled(true);
                    changePasswordButton.setEnabled(true);
                    registerButton.setEnabled(true);
                });
                System.out.println("加载完成");
            } catch (Exception e) {
                // 在事件调度线程上显示错误信息
                SwingUtilities.invokeLater(() ->
                        JOptionPane.showMessageDialog(this, "数据加载失败")
                );
                throw new RuntimeException(e);
            }
        }).start();
    }

    /**
     * 执行登录操作
     */
    private void performLogin() {
        String username = this.username.getText().trim(); // 去除首尾空格
        char[] passwordChars = this.password.getPassword();
        String password = new String(passwordChars);

        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this, "账号不能为空");
            return;
        }
        if (password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "密码不能为空");
            return;
        }

        // 验证用户账号和密码
        boolean loginSuccess = false;

        for (Customer customer : customers) {
            if (customer.getCustomerName().equals(username) && customer.getCustomerPassword().equals(password)) {
                dispose();
                EventQueue.invokeLater(() -> {
                    try {
                        if (customer.getCustomerID() == 1001) { // 如果是管理员
                            RootMain frame = new RootMain(); // 打开管理员界面
                            frame.setVisible(true);
                        } else { // 普通用户
                            int customerId = customer.getCustomerID();
                            UserMain frame = new UserMain(customerId); // 打开用户主界面
                            frame.setVisible(true);
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
                loginSuccess = true;
                break;
            }
        }

        // 如果账号和密码都不匹配
        if (!loginSuccess) {
            JOptionPane.showMessageDialog(this, "账号或密码错误");
        }
    }

    /**
     * 修改密码窗口
     */
    public void change() {
        JPanel panel = new JPanel(new GridBagLayout());
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
        panel.add(new JLabel("账号:"), gbc);
        gbc.gridx = 1;
        JTextField username = new JTextField();
        username.setPreferredSize(new Dimension(200, 30));
        panel.add(username, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("旧密码:"), gbc);
        gbc.gridx = 1;
        JTextField oldPassword = new JTextField();
        oldPassword.setPreferredSize(new Dimension(200, 30));
        panel.add(oldPassword, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("新密码:"), gbc);
        gbc.gridx = 1;
        JTextField newPassword = new JTextField();
        newPassword.setPreferredSize(new Dimension(200, 30));
        panel.add(newPassword, gbc);

        int result = JOptionPane.showConfirmDialog(
                this,
                panel,
                "修改密码",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        // 根据用户的选择处理结果
        if (result == JOptionPane.OK_OPTION) {
            changePassword(username.getText(), oldPassword.getText(), newPassword.getText());
        }
    }

    /**
     * 修改密码
     */
    public void changePassword(String username, String oldPassword, String newPassword) {
        if ("".equals(username) || username == null) {
            JOptionPane.showMessageDialog(this, "账号不能为空");
            return;
        }
        if ("".equals(oldPassword) || oldPassword == null) {
            JOptionPane.showMessageDialog(this, "旧密码不能为空");
            return;
        }
        if ("".equals(newPassword) || newPassword == null) {
            JOptionPane.showMessageDialog(this, "新密码不能为空");
            return;
        }

        for (Customer customer : customers) {
            if (!customer.getCustomerName().equals(username)) {
                continue;
            }

            if (customer.getCustomerPassword().equals(oldPassword)) {
                customer.setCustomerPassword(newPassword);
                try {
                    customerMethod.updateCustomer(customer);
                    JOptionPane.showMessageDialog(this, "密码修改成功");
                    return;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else {
                JOptionPane.showMessageDialog(this, "旧密码输入错误，请联系管理员");
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "不存在该账号");
    }

    /**
     * 注册账号窗口
     */
    public void register() {
        JPanel panel = new JPanel(new GridBagLayout());
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
        panel.add(new JLabel("账号:"), gbc);
        gbc.gridx = 1;
        JTextField username = new JTextField();
        username.setPreferredSize(new Dimension(200, 30));
        panel.add(username, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("密码:"), gbc);
        gbc.gridx = 1;
        JTextField password = new JTextField();
        password.setPreferredSize(new Dimension(200, 30));
        panel.add(password, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("电话:"), gbc);
        gbc.gridx = 1;
        JTextField phone = new JTextField();
        phone.setPreferredSize(new Dimension(200, 30));
        panel.add(phone, gbc);

        int result = JOptionPane.showConfirmDialog(
                this,
                panel,
                "账号注册",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        // 根据用户的选择处理结果
        if (result == JOptionPane.OK_OPTION) {
            registerAccount(username.getText(), password.getText(), phone.getText());
        }
    }

    /**
     * 注册账号
     */
    private void registerAccount(String username, String password, String phone) {
        // 检查用户名、密码和电话是否为空
        if ("".equals(username) || username == null) {
            JOptionPane.showMessageDialog(this, "账号不能为空");
            return;
        }
        if ("".equals(password) || password == null) {
            JOptionPane.showMessageDialog(this, "密码不能为空");
            return;
        }
        if ("".equals(phone) || phone == null) {
            JOptionPane.showMessageDialog(this, "电话不能为空");
            return;
        }

        // 验证输入格式是否符合要求
        if (username.length() >= 2 && username.length() < 12
                && password.length() > 5 && password.length() < 12
                && phone.length() == 11) {

            // 检查账号是否已经被注册
            for (Customer customer : customers) {
                if (customer.getCustomerName().equals(username)) {
                    JOptionPane.showMessageDialog(this, "账号已被人注册");
                    return;
                }
            }

            loginButton.setEnabled(false);
            changePasswordButton.setEnabled(false);
            registerButton.setEnabled(false);

            // 创建一个新的顾客对象并设置其信息
            Customer newCustomer = new Customer();
            newCustomer.setCustomerName(username);
            newCustomer.setCustomerPassword(password);
            newCustomer.seamyCall(phone);
            newCustomer.setOrderNumber("");  // 设置空订单号（可以根据需求调整）

            try {
                customerMethod.addCustomer(newCustomer); // 将新顾客信息添加到数据库
                loadCustomerData();
                JOptionPane.showMessageDialog(this, "注册成功");
            } catch (Exception ex) {
                throw new RuntimeException(ex);  // 如果添加顾客失败，抛出异常
            }
        } else {
            // 如果输入格式不符合要求，弹出提示信息
            JOptionPane.showMessageDialog(this, "账号2~12位之间，密码5~12位之间，手机号11位");
        }
    }
}
