package MainFrom.RootFrom;

import java.awt.*;
import java.util.List;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import MainFrom.MyPanel;
import method.RoomMethod;
import model.Room;

public class RoomManagePanel extends MyPanel {

    RoomMethod roomMethod = new RoomMethod();
    public List<Room> rooms;

    /**
     * 创建房间管理面板
     */
    public RoomManagePanel() {
        // 加载房间按钮
        gbc.gridx = 0;
        gbc.gridy = 0;
        JButton loadRoomButton = new JButton("加载房间");
        loadRoomButton.addActionListener(e -> getRooms());
        topPanel.add(loadRoomButton, gbc);

        // 修改房间按钮
        gbc.gridx = 1;
        JButton updateRoomButton = new JButton("修改房间");
        updateRoomButton.addActionListener(e -> updateRoomWindow());
        topPanel.add(updateRoomButton, gbc);

        // 添加房间按钮
        gbc.gridx = 2;
        JButton addRoomButton = new JButton("添加房间");
        addRoomButton.addActionListener(e -> addRoomWindow());
        topPanel.add(addRoomButton, gbc);

        // 搜索房间按钮
        gbc.gridx = 3;
        JButton searchRoomButton = new JButton("搜索房间");
        searchRoomButton.addActionListener(e -> searchRooms());
        topPanel.add(searchRoomButton, gbc);

        // 创建房间表格
        table.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{
                        "房间号", "类型", "价格", "状态"
                }
        ));

        getRooms();
    }

    /**
     * 获取房间信息
     */
    private void getRooms() {
        // 异步加载房间数据
        Runnable loadTask = () -> {
            System.out.println("🙌🙌🙌异步加载房间信息🙌🙌🙌");
            try {
                rooms = roomMethod.query();
            } catch (Exception e) {
                SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(this, "加载房间信息失败，请重试！"));
                throw new RuntimeException(e);
            }
            SwingUtilities.invokeLater(this::refreshRoomTableData); // 在事件调度线程中更新 UI
            System.out.println("🎉🎉🎉异步加载房间信息完成🎉🎉🎉");
        };
        new Thread(loadTask).start(); // 启动异步线程
    }

    /**
     * 刷新房间表格数据
     *
     * @param rooms 房间列表
     */
    public void refreshRoomTableData(List<Room> rooms) {
        Runnable refreshTask = () -> {
            System.out.println("异步刷新房间表格数据");
            DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
            // 清空表格数据
            while (tableModel.getRowCount() > 0) {
                tableModel.removeRow(tableModel.getRowCount() - 1);
            }
            // 添加新的数据
            for (Room room : rooms) {
                Vector<Object> row = new Vector<>();
                row.add(room.getRoomNumber());
                row.add(room.getRoomType());
                row.add(room.getRoomPrice());
                row.add(room.getRoomState());
                tableModel.addRow(row);
            }
            System.out.println("异步刷新房间表格数据完成");
        };
        new Thread(refreshTask).start();
    }

    public void refreshRoomTableData() {
        refreshRoomTableData(rooms);
    }

    /**
     * 添加房间窗口
     */
    private void addRoomWindow() {
        // 创建主面板，使用 GridBagLayout 来设置灵活的布局
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder("填写信息"),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5)
                )
        );

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // 设置组件间距
        gbc.anchor = GridBagConstraints.WEST; // 设置组件的对齐方式（左对齐）

        // 创建并添加房间号标签和输入框
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("房间号:"), gbc);
        gbc.gridx = 1;
        JTextField roomNumberField = new JTextField();
        roomNumberField.setPreferredSize(new Dimension(200, 30));
        panel.add(roomNumberField, gbc);

        // 创建并添加房间类型标签和下拉框
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("类型:"), gbc);
        gbc.gridx = 1;
        JComboBox<String> typeComboBox = new JComboBox<>(new String[]{"单人间", "双人间", "豪华套房"});
        typeComboBox.setPreferredSize(new Dimension(200, 30));
        panel.add(typeComboBox, gbc);

        // 创建并添加房间价格标签和输入框
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("价格:"), gbc);
        gbc.gridx = 1;
        JTextField priceField = new JTextField();
        priceField.setPreferredSize(new Dimension(200, 30));
        panel.add(priceField, gbc);

        // 创建并添加房间状态标签和下拉框
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("状态:"), gbc);
        gbc.gridx = 1;
        JComboBox<String> statusComboBox = new JComboBox<>(new String[]{"空闲", "已入住"});
        statusComboBox.setPreferredSize(new Dimension(200, 30));
        panel.add(statusComboBox, gbc);

        int result = JOptionPane.showConfirmDialog(
                this,
                panel,
                "添加房间",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        // 根据用户的选择处理结果
        if (result == JOptionPane.OK_OPTION) {
            String roomNumber = roomNumberField.getText().trim();
            String priceText = priceField.getText().trim();

            // 检查输入是否有效
            if (roomNumber.isEmpty() || priceText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "请填写所有字段！");
                return;
            }

            try {
                int price = Integer.parseInt(priceText);
                addRoom(roomNumber, (String) typeComboBox.getSelectedItem(), price, statusComboBox.getSelectedIndex());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "价格必须是有效的数字！");
            }
        }
    }

    /**
     * 添加房间
     */
    private void addRoom(String roomNumber, String roomType, int price, int status) {
        // 检查房间号是否已存在
        if (rooms.stream().anyMatch(room -> room.getRoomNumber().equals(roomNumber))) {
            JOptionPane.showMessageDialog(this, "房间号已存在，请输入一个新的房间号！");
            return;
        }

        Room newRoom = new Room();
        newRoom.setRoomNumber(roomNumber);
        newRoom.setRoomType(roomType);
        newRoom.setRoomPrice(price);
        newRoom.setRoomState(status);

        // 添加到本地 rooms
        rooms.add(newRoom);

        // 刷新表格
        refreshRoomTableData();

        // 异步更新数据库
        Runnable addTask = () -> {
            System.out.println("异步添加房间到数据库");
            try {
                roomMethod.addCustomer(newRoom); // 添加到数据库
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            System.out.println("异步添加房间到数据库完成");
            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(this, "房间添加完成！"));
        };
        new Thread(addTask).start();
    }

    /**
     * 修改房间窗口
     */
    private void updateRoomWindow() {
        // 创建主面板，使用 GridBagLayout 来设置灵活的布局
        JPanel panel = new JPanel(new GridBagLayout());
        // 在 panel 面板的外层添加了一个复合边框
        panel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder("填写信息"), // 外层，带标题的边框
                        BorderFactory.createEmptyBorder(5, 5, 5, 5) // 内层，空边框（提供内边距）
                )
        );

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // 设置组件间距
        gbc.anchor = GridBagConstraints.WEST; // 设置组件的对齐方式（左对齐）

        // 房间类型
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("类型:"), gbc);
        gbc.gridx = 1;
        JComboBox<String> typeComboBox = new JComboBox<>(new String[]{"单人间", "双人间", "豪华套房"});
        typeComboBox.setPreferredSize(new Dimension(200, 30));
        panel.add(typeComboBox, gbc);

        // 价格
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("价格:"), gbc);
        gbc.gridx = 1;
        JTextField priceField = new JTextField();
        priceField.setPreferredSize(new Dimension(200, 30));
        panel.add(priceField, gbc);

        // 对话框
        int result = JOptionPane.showConfirmDialog(
                this,
                panel,
                "修改房间",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        // 根据用户的选择处理结果
        if (result == JOptionPane.OK_OPTION) {
            String priceText = priceField.getText().trim();

            // 检查输入是否有效
            if (priceText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "请输入价格！");
                return;
            }

            try {
                int newPrice = Integer.parseInt(priceText);
                updateRoom((String) typeComboBox.getSelectedItem(), newPrice);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "价格必须是有效的数字！");
            }
        }
    }

    /**
     * 按房间类型修改价格
     */
    private void updateRoom(String roomType, int newPrice) {
        // 更新本地 rooms 数据
        for (Room room : rooms) {
            if (room.getRoomType().equals(roomType)) {
                room.setRoomPrice(newPrice);
            }
        }

        // 刷新表格
        refreshRoomTableData();

        // 异步更新数据库
        Runnable updateTask = () -> {
            System.out.println("异步更新房间到数据库");
            for (Room room : rooms) {
                if (room.getRoomType().equals(roomType)) {
                    try {
                        roomMethod.updateCustomer(room); // 更新到数据库
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            System.out.println("异步更新房间到数据库完成");
            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(this, "房间修改完成！"));
        };
        new Thread(updateTask).start();
    }

    /**
     * 搜索房间
     */
    private void searchRooms() {
        String keyword = JOptionPane.showInputDialog(this, "请输入房间号:", "搜索房间", JOptionPane.PLAIN_MESSAGE);
        if (keyword != null && !keyword.trim().isEmpty()) {
            List<Room> filteredRooms = rooms.stream()
                    .filter(room -> room.getRoomNumber().contains(keyword))
                    .toList();
            refreshRoomTableData(filteredRooms);
        }
    }
}
