package model;

import java.util.Date;

public class Contact {
    private String orderNumber;  // 订单编号
    private String homeNumber;    // 房间编号
    private Date endTime;         // 入住时间

    // 构造方法
    public Contact() {
    }

    // Getter 和 Setter 方法
    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getHomeNumber() {
        return homeNumber;
    }

    public void setHomeNumber(String homeNumber) {
        this.homeNumber = homeNumber;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "Contact [orderNumber=" + orderNumber + ", homeNumber=" + homeNumber + ", endTime=" + endTime + "]";
    }
}
