package MainFrom.RootFrom;

import java.awt.*;
import java.util.List;
import javax.swing.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import method.HotelMethod;
import model.Hotel;

public class HotelManagePanel extends JPanel {

    private static final Logger logger = LoggerFactory.getLogger(HotelManagePanel.class);

    private final JTextField hotelName, hotelPhone, hotelAddress;
    private final JLabel nameDisplayLabel, phoneDisplayLabel, addressDisplayLabel;
    HotelMethod hotelMethod = new HotelMethod();

    /**
     * 创建酒店管理面板
     */
    public HotelManagePanel() {
        getHotel();
        // 设置布局
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10, 10, 10, 10); // 设置按钮之间的间距
        gbc.anchor = GridBagConstraints.WEST; // 设置组件的对齐方式（左对齐）
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel nameLabel = new JLabel("名称:");
        add(nameLabel, gbc);

        gbc.gridy = 1;
        JLabel phoneLabel = new JLabel("电话:");
        add(phoneLabel, gbc);

        gbc.gridy = 2;
        JLabel addressLabel = new JLabel("地址:");
        add(addressLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        nameDisplayLabel = new JLabel();
        add(nameDisplayLabel, gbc);

        gbc.gridy = 1;
        phoneDisplayLabel = new JLabel();
        add(phoneDisplayLabel, gbc);

        gbc.gridy = 2;
        addressDisplayLabel = new JLabel();
        add(addressDisplayLabel, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        hotelName = new JTextField();
        add(hotelName, gbc);
        hotelName.setColumns(10);

        gbc.gridy = 1;
        hotelPhone = new JTextField();
        add(hotelPhone, gbc);
        hotelPhone.setColumns(10);

        gbc.gridy = 2;
        hotelAddress = new JTextField();
        add(hotelAddress, gbc);
        hotelAddress.setColumns(10);

        // 创建按钮并设置操作
        gbc.gridx = 3;
        gbc.gridy = 0;
        JButton updateNameButton = new JButton("修改");
        updateNameButton.addActionListener(event -> updateHotelName());
        add(updateNameButton, gbc);

        gbc.gridy = 1;
        JButton updatePhoneButton = new JButton("修改");
        updatePhoneButton.addActionListener(event -> updateHotelPhone());
        add(updatePhoneButton, gbc);

        gbc.gridy = 2;
        JButton updateAddressButton = new JButton("修改");
        updateAddressButton.addActionListener(event -> updateHotelAddress());
        add(updateAddressButton, gbc);
    }

    /**
     * 获取酒店信息
     */
    public void getHotel() {
        Runnable loadTask = () -> {
            System.out.println("🙌🙌🙌异步加载酒店信息🙌🙌🙌");
            try {
                List<Hotel> hotels = hotelMethod.query();
                if (hotels != null && !hotels.isEmpty()) {
                    Hotel hotel = hotels.get(0);

                    // 确保 UI 更新在主线程
                    SwingUtilities.invokeLater(() -> {
                        nameDisplayLabel.setText(hotel.getHotelName());
                        phoneDisplayLabel.setText(hotel.getHotelPhone());
                        addressDisplayLabel.setText(hotel.getHotelAddress());
                    });
                    System.out.println("🎉🎉🎉异步加载酒店信息完成🎉🎉🎉");
                }
            } catch (Exception e) {
                logger.error("获取酒店信息失败", e);
                JOptionPane.showMessageDialog(this, "加载酒店信息失败，请稍后重试！");
            }
        };
        new Thread(loadTask).start();
    }

    /**
     * 修改酒店名称
     */
    private void updateHotelName() {
        Runnable loadTask = () -> {
            String newName = hotelName.getText();
            if (newName.length() < 3) {
                JOptionPane.showMessageDialog(this, "名称过短");
                return;
            }
            HotelMethod hotelMethod = new HotelMethod();
            try {
                Hotel hotel = hotelMethod.query().get(0);
                hotel.setHotelName(newName);
                hotelMethod.updateHotelInfo(hotel);
                getHotel();
                JOptionPane.showMessageDialog(this, "修改成功");
            } catch (Exception e) {
                logger.error("更新酒店名称失败", e);
                JOptionPane.showMessageDialog(this, "修改失败，请稍后重试！");
            }
        };
        new Thread(loadTask).start();
    }

    /**
     * 修改酒店电话
     */
    private void updateHotelPhone() {
        Runnable loadTask = () -> {
            String newPhone = hotelPhone.getText();
            if (newPhone.length() != 11) {
                JOptionPane.showMessageDialog(this, "请输入正确的手机号");
                return;
            }
            HotelMethod hotelMethod = new HotelMethod();
            try {
                Hotel hotel = hotelMethod.query().get(0);
                hotel.setHotelPhone(newPhone);
                hotelMethod.updateHotelInfo(hotel);
                getHotel();
                JOptionPane.showMessageDialog(this, "修改成功");
            } catch (Exception e) {
                logger.error("更新酒店电话失败", e);
                JOptionPane.showMessageDialog(this, "修改失败，请稍后重试！");
            }
        };
        new Thread(loadTask).start();
    }

    /**
     * 修改酒店地址
     */
    private void updateHotelAddress() {
        Runnable loadTask = () -> {
            String newAddress = hotelAddress.getText();
            if (newAddress.length() < 5) {
                JOptionPane.showMessageDialog(this, "地址过短");
                return;
            }
            HotelMethod hotelMethod = new HotelMethod();
            try {
                Hotel hotel = hotelMethod.query().get(0);
                hotel.setHotelAddress(newAddress);
                hotelMethod.updateHotelInfo(hotel);
                getHotel();
                JOptionPane.showMessageDialog(this, "修改成功");
            } catch (Exception e) {
                logger.error("更新酒店地址失败", e);
                JOptionPane.showMessageDialog(this, "修改失败，请稍后重试！");
            }
        };
        new Thread(loadTask).start();
    }
}
