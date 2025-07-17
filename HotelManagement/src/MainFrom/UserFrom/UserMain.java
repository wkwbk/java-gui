package MainFrom.UserFrom;

import MainFrom.MyFrame;

import javax.swing.*;
import java.awt.*;

/**
 * @projectName: HotelManagement
 * @package: MainFrom.UserFrom
 * @className: UserMain
 * @author: LI SIR
 * @description: UserMain 类是用户界面的主框架，继承自 MyFrame 类。
 *               提供普通用户的功能入口，包括主页、住宿查询、
 *               我的订单和个人中心等模块的导航。
 * @date: 2024/12/23 23:24
 * @version: 1.0
 */
public class UserMain extends MyFrame {

    public UserMain(int userID) {
        setTitle("用户界面"); // 设置窗口标题

        // 添加选项卡
        addTab("主页", null, getHomePanel(), "欢迎界面");
        addTab("住宿查询", null, new RoomSearchPanel(userID), "住宿查询界面");
        addTab("我的订单", null, new UserOrderPanel(userID), "我的订单界面");
        addTab("个人中心", null, new UserCenterPanel(userID), "个人中心界面");
    }

    /**
     * 获取主页面板
     *
     * @return JPanel
     */
    private JPanel getHomePanel() {
        JPanel homePanel = new JPanel();
        homePanel.setLayout(null); // 使用绝对布局

        JLabel welcomeLabel = new JLabel("Welcome to 226 International hotel\r\n");
        welcomeLabel.setFont(new Font("宋体", Font.BOLD, 18));
        welcomeLabel.setBounds(10, 25, 500, 30);
        homePanel.add(welcomeLabel);

        JLabel worldToOneLabel = new JLabel("To the world you may be one person ");
        worldToOneLabel.setFont(new Font("宋体", Font.ITALIC, 15));
        worldToOneLabel.setBounds(10, 88, 300, 21);
        homePanel.add(worldToOneLabel);

        JLabel oneToWorldLabel = new JLabel("but to us you may be the word");
        oneToWorldLabel.setFont(new Font("宋体", Font.ITALIC, 15));
        oneToWorldLabel.setBounds(54, 150, 300, 21);
        homePanel.add(oneToWorldLabel);

        JLabel chineseWorldToOneLabel = new JLabel("对于世界而言，你是一个人；");
        chineseWorldToOneLabel.setFont(new Font("宋体", Font.ITALIC, 15));
        chineseWorldToOneLabel.setBounds(39, 119, 200, 21);
        homePanel.add(chineseWorldToOneLabel);

        JLabel chineseOneToWorldLabel = new JLabel("但是对于我们，你是我们的整个世界。");
        chineseOneToWorldLabel.setFont(new Font("宋体", Font.ITALIC, 15));
        chineseOneToWorldLabel.setBounds(104, 181, 300, 21);
        homePanel.add(chineseOneToWorldLabel);

        return homePanel;
    }
}
