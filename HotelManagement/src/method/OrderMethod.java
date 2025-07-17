package method;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import hotel.DBUtil;
import model.Order;

public class OrderMethod {
    // 插入条信息
    public void addCustomer(Order g) throws Exception {
        Connection conn = DBUtil.getConnection();
        String SQL = "insert into order_details(orderNumber,customerID,roomType,orderState,startTime) " + " values(?,?,?,?,?);";
        PreparedStatement stmt = conn.prepareStatement(SQL);
        stmt.setString(1, g.getOrderNumber());
        stmt.setInt(2, g.getCustomerID());
        stmt.setString(3, g.getRoomType());
        stmt.setString(4, g.getOrderState());
        stmt.setDate(5, new Date(g.getStartTime().getTime()));
        stmt.execute();
    }

    // 刷新一条信息
    public void updateCustomer(Order g) throws Exception {
        Connection conn = DBUtil.getConnection();
        String SQL = "update order_details set orderNumber=?,customerID=?,roomType=?,orderState=?,startTime=?" + " where orderNumber=?";
        PreparedStatement stmt = conn.prepareStatement(SQL);
        stmt.setString(1, g.getOrderNumber());
        stmt.setInt(2, g.getCustomerID());
        stmt.setString(3, g.getRoomType());
        stmt.setString(4, g.getOrderState());
        stmt.setDate(5, new Date(g.getStartTime().getTime()));
        stmt.setString(6, g.getOrderNumber());
        stmt.execute();
    }

    // 删除一条信息
    public void deleteCustomer(String orderNumber) throws SQLException {
        Connection conn = DBUtil.getConnection();
        String SQL = " delete from order_details " + "  where orderNumber=?";
        PreparedStatement stmt = conn.prepareStatement(SQL);
        stmt.setString(1, orderNumber);
        stmt.execute();
    }

    // 返回表中所有信息到一个集合中
    public List<Order> query() throws Exception {
        Connection conn = DBUtil.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select * from order_details");
        List<Order> gs = new ArrayList<>();
        Order g;
        while (rs.next()) {
            g = new Order();
            g.setOrderNumber(rs.getString("orderNumber"));
            g.setCustomerID(rs.getInt("customerID"));
            g.setRoomType(rs.getString("roomType"));
            g.setOrderState(rs.getString("orderState"));
            g.setStartTime(rs.getDate("startTime"));
            gs.add(g);
        }
        return gs;
    }

    public Order getID(String orderNumber) throws Exception {
        Connection conn = DBUtil.getConnection();
        String SQL = "select orderNumber,customerID,roomType,orderState,startTime from order_details" + " where orderNumber=?";
        PreparedStatement stmt = conn.prepareStatement(SQL);
        stmt.setString(1, orderNumber);
        ResultSet rs = stmt.executeQuery();
        Order g = null;
        while (rs.next()) {
            g = new Order();
            g.setOrderNumber(rs.getString("orderNumber"));
            g.setCustomerID(rs.getInt("customerID"));
            g.setRoomType(rs.getString("roomType"));
            g.setOrderState(rs.getString("orderState"));
            g.setStartTime(rs.getDate("startTime"));
        }
        return g;
    }

    public Order get(int id) throws Exception {
        Connection conn = DBUtil.getConnection();
        String SQL = "select orderNumber,customerID,roomType,orderState,startTime from order_details" + " where customerID=?";
        PreparedStatement stmt = conn.prepareStatement(SQL);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        Order g = null;
        while (rs.next()) {
            g = new Order();
            g.setOrderNumber(rs.getString("orderNumber"));
            g.setCustomerID(rs.getInt("customerID"));
            g.setRoomType(rs.getString("roomType"));
            g.setOrderState(rs.getString("orderState"));
            g.setStartTime(rs.getDate("startTime"));
        }
        return g;
    }
}
