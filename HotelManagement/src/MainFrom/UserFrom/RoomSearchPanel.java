package MainFrom.UserFrom;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import method.ContactMethod;
import method.RoomMethod;
import method.OrderMethod;
import model.Contact;
import model.Order;
import model.Room;

import java.text.SimpleDateFormat;

public class RoomSearchPanel extends JPanel {

    public volatile boolean homesLoaded = false;
    public SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
    public OrderMethod orderMethod = new OrderMethod(); //获取订单表的操作方法
    public ContactMethod contactMethod = new ContactMethod();
    public RoomMethod roomMethod = new RoomMethod(); //获取顾客表的操作方法
    public JComboBox<Integer> daysComboBox1, daysComboBox2, daysComboBox3;
    public JLabel countLabel1, countLabel2, countLabel3;
    public JLabel priceLabel1, priceLabel2, priceLabel3;
    public List<Room> rooms; //返回顾客信息到List中
    public int userID;

    public RoomSearchPanel(int userID) {
        this.userID = userID;
        getHomes();
        // 设置布局
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("房间类型"), gbc);

        gbc.gridx = 1;
        add(new JLabel("房间价格"), gbc);

        gbc.gridx = 2;
        add(new JLabel("房间余量"), gbc);

        gbc.gridx = 3;
        add(new JLabel("时间（天）"), gbc);

        gbc.gridx = 4;
        JButton gerRoomsButton = new JButton("加载");
        add(gerRoomsButton, gbc);
        gerRoomsButton.addActionListener(e -> getHomes());

        // 添加行内容示例（单人间）
        gbc.gridy = 1;
        gbc.gridx = 0;
        JLabel roomType1 = new JLabel("单人间");
        add(roomType1, gbc);

        gbc.gridx = 1;
        priceLabel1 = new JLabel("价格待定");
        add(priceLabel1, gbc);

        gbc.gridx = 2;
        countLabel1 = new JLabel("余量待定");
        add(countLabel1, gbc);

        gbc.gridx = 3;
        daysComboBox1 = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
        add(daysComboBox1, gbc);

        gbc.gridx = 4;
        JButton singleRoomButton = new JButton("下单");
        add(singleRoomButton, gbc);
        singleRoomButton.addActionListener(e -> reserveRoom(roomType1.getText(), countLabel1, daysComboBox1));

        // 添加行内容示例
        gbc.gridy = 2;
        gbc.gridx = 0;
        JLabel roomType2 = new JLabel("双人间");
        add(roomType2, gbc);

        gbc.gridx = 1;
        priceLabel2 = new JLabel("价格待定");
        add(priceLabel2, gbc);

        gbc.gridx = 2;
        countLabel2 = new JLabel("余量待定");
        add(countLabel2, gbc);

        gbc.gridx = 3;
        daysComboBox2 = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
        add(daysComboBox2, gbc);

        gbc.gridx = 4;
        JButton doubleRoomButton = new JButton("下单");
        add(doubleRoomButton, gbc);
        doubleRoomButton.addActionListener(e -> reserveRoom(roomType2.getText(), countLabel2, daysComboBox2));

        // 添加行内容示例
        gbc.gridy = 3;
        gbc.gridx = 0;
        JLabel roomType3 = new JLabel("豪华套房");
        add(roomType3, gbc);

        gbc.gridx = 1;
        priceLabel3 = new JLabel("价格待定");
        add(priceLabel3, gbc);

        gbc.gridx = 2;
        countLabel3 = new JLabel("余量待定");
        add(countLabel3, gbc);

        gbc.gridx = 3;
        daysComboBox3 = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
        add(daysComboBox3, gbc);

        gbc.gridx = 4;
        JButton deluxeSuiteButton = new JButton("下单");
        add(deluxeSuiteButton, gbc);
        deluxeSuiteButton.addActionListener(e -> reserveRoom(roomType3.getText(), countLabel3, daysComboBox3));
    }

    /**
     * 获取酒店信息
     */
    public void getHomes() {
        new Thread(() -> {
            try {
                System.out.println("🙌🙌🙌异步加载房间信息🙌🙌🙌");
                rooms = roomMethod.query();
                homesLoaded = true; // 加载完成
                System.out.println("🎉🎉🎉异步加载房间信息完成🎉🎉🎉");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            SwingUtilities.invokeLater(this::refreshRoomsNumber); // 刷新界面
        }).start();
    }

    /**
     * 刷新房间数量
     */
    public void refreshRoomsNumber() {
        //刷新房间数量
        Runnable loadTask = () -> {
            int oneNumber = 0, twoNumber = 0, threeNumber = 0;
            for (Room home : rooms) {
                if (home.getRoomState() == 0) {
                    if (home.getRoomType().equals("单人间")) oneNumber++;
                    if (home.getRoomType().equals("双人间")) twoNumber++;
                    if (home.getRoomType().equals("豪华套房")) threeNumber++;
                }
            }
            countLabel1.setText("" + oneNumber);
            countLabel2.setText("" + twoNumber);
            countLabel3.setText("" + threeNumber);
            for (Room home : rooms) {
                if (home.getRoomType().equals("单人间")) {
                    priceLabel1.setText("" + home.getRoomPrice());
                }
                if (home.getRoomType().equals("双人间")) {
                    priceLabel2.setText("" + home.getRoomPrice());
                }
                if (home.getRoomType().equals("豪华套房")) {
                    priceLabel3.setText("" + home.getRoomPrice());
                }
            }
        };
        new Thread(loadTask).start();
    }

    public void reserveRoom(String roomType, JLabel countLabel, JComboBox<Integer> daysComboBox) {
        if (!homesLoaded) {
            JOptionPane.showMessageDialog(this, "房间信息尚未加载完成，请稍后再试！");
            return;
        }

        // 刷新房间数量
        int roomNumber = 0;
        for (Room room : rooms) {
            if (room.getRoomState() == 0 && room.getRoomType().equals(roomType)) {
                roomNumber++;
            }
        }
        countLabel.setText("" + roomNumber);

        if (roomNumber == 0) {
            JOptionPane.showMessageDialog(this, roomType + "没有了，抱歉");
            return;
        }

        // 获取时间
        Date date = new Date();
        String orderNumber = dateFormat.format(date);

        // 添加订单
        Order order = new Order();
        order.setOrderState("1");
        order.setCustomerID(userID);
        order.setStartTime(date);
        order.setOrderNumber(orderNumber);
        order.setRoomType(roomType);

        new Thread(() -> {
            try {
                orderMethod.addCustomer(order);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();

        // 添加天数
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, daysComboBox.getSelectedIndex() + 1);
        Contact contact = new Contact();
        contact.setOrderNumber(orderNumber);
        contact.setEndTime(calendar.getTime());

        for (Room room : rooms) {
            if (room.getRoomState() == 0 && room.getRoomType().equals(roomType)) {
                room.setRoomState(1); // 更新房间状态
                contact.setHomeNumber(room.getRoomNumber());
                new Thread(() -> {
                    try {
                        contactMethod.addContact(contact);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        roomMethod.updateCustomer(room);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }).start();
                roomNumber--;
                countLabel.setText("" + roomNumber);
                JOptionPane.showMessageDialog(this, "下单成功：房间为 " + room.getRoomNumber() + " 请准时入住");
                return;
            }
        }
    }
}
