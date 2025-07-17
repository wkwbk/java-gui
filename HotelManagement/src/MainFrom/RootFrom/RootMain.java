package MainFrom.RootFrom;

import MainFrom.MyFrame;

import javax.swing.*;
import java.awt.*;

/**
 * @projectName: HotelManagement
 * @package: MainFrom.RootFrom
 * @className: RootMain
 * @author: LI SIR
 * @description: RootMain 类是管理员界面的主框架，继承自 MyFrame 类。
 *               提供管理员的功能入口，包括主页、房间管理、订单管理、
 *               客户管理和酒店管理等模块的导航。
 * @date: 2024/12/23 23:24
 * @version: 1.0
 */
public class RootMain extends MyFrame {

    public RootMain() {
        setTitle("管理员界面"); // 设置窗口标题

        // 添加选项卡
        addTab("主页", null, getHomePanel(), "欢迎界面");
        addTab("房间管理", null, new RoomManagePanel(), "房间管理界面");
        addTab("订单管理", null, new OrderManagePanel(), "订单管理界面");
        addTab("客户管理", null, new CustomerManagePanel(), "客户管理界面");
        addTab("酒店管理", null, new HotelManagePanel(), "酒店管理界面");
    }

    /**
     * 获取主页面板
     *
     * @return JPanel
     */
    private JPanel getHomePanel() {
        JPanel homePanel = new JPanel();
        homePanel.setLayout(null); // 使用绝对布局

        // 欢迎信息标签
        JLabel welcomeLabel = new JLabel("Welcome to 226 International hotel");
        welcomeLabel.setFont(new Font("宋体", Font.BOLD, 18));
        welcomeLabel.setBounds(10, 27, 424, 30); // 设置位置和大小
        homePanel.add(welcomeLabel);

        // 副标题标签
        JLabel subtitleLabel = new JLabel("为你所想 为你所乐 为我人生 创造弯道");
        subtitleLabel.setBounds(186, 67, 248, 50); // 设置位置和大小
        homePanel.add(subtitleLabel);
        return homePanel;
    }
}
