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
import model.Contact;

public class ContactMethod {
    // 插入条信息
    public void addContact(Contact g) throws Exception {
        Connection conn = DBUtil.getConnection();
        String SQL = "insert into contact(orderNumber,roomNumber,endTime)" + " values(?,?,?)";
        PreparedStatement stmt = conn.prepareStatement(SQL);
        stmt.setString(1, g.getOrderNumber());
        stmt.setString(2, g.getHomeNumber());
        stmt.setDate(3, new Date(g.getEndTime().getTime()));
        stmt.execute();
    }

    // 刷新一条信息
    public void updateContact(Contact g) throws Exception {
        Connection conn = DBUtil.getConnection();
        String SQL = "update contact set orderNumber=?,roomNumber=?,endTime=?" + " where orderNumber=? and roomNumber=?";
        PreparedStatement stmt = conn.prepareStatement(SQL);
        stmt.setString(1, g.getOrderNumber());
        stmt.setString(2, g.getHomeNumber());
        stmt.setDate(3, new Date(g.getEndTime().getTime()));
        stmt.setString(4, g.getOrderNumber());
        stmt.setString(5, g.getHomeNumber());
        stmt.execute();
    }

    // 删除一条信息
    public void deleteContact(String orderNumber) throws SQLException {
        Connection conn = DBUtil.getConnection();
        String SQL = " delete from contact " + " where orderNumber=? ";
        PreparedStatement stmt = conn.prepareStatement(SQL);
        stmt.setString(1, orderNumber);
        stmt.execute();
    }

    // 返回表中所有信息到一个集合中
    public List<Contact> query() throws Exception {
        Connection conn = DBUtil.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select * from contact");
        List<Contact> gs = new ArrayList<>();
        Contact g;
        while (rs.next()) {
            g = new Contact();
            g.setOrderNumber(rs.getString("orderNumber"));
            g.setHomeNumber(rs.getString("roomNumber"));
            g.setEndTime(rs.getDate("endTime"));
            gs.add(g);
        }
        return gs;
    }

    // 查找orderNumber的信息
    public Contact getContact(String orderNumber) throws Exception {
        Connection conn = DBUtil.getConnection();
        String SQL = "select orderNumber,roomNumber,endTime from contact" + " where orderNumber=?";
        PreparedStatement stmt = conn.prepareStatement(SQL);
        stmt.setString(1, orderNumber);
        ResultSet rs = stmt.executeQuery();
        Contact g = null;
        while (rs.next()) {
            g = new Contact();
            g.setOrderNumber(rs.getString("orderNumber"));
            g.setHomeNumber(rs.getString("roomNumber"));
            g.setEndTime(rs.getDate("endTime"));
        }
        return g;
    }

    // 得到roomNumber的信息
    public Contact getRoom(String roomNumber) throws Exception {
        Connection conn = DBUtil.getConnection();
        String SQL = "select orderNumber,roomNumber,endTime from contact" + " where roomNumber=?";
        PreparedStatement stmt = conn.prepareStatement(SQL);
        stmt.setString(1, roomNumber);
        ResultSet rs = stmt.executeQuery();
        Contact g = null;
        while (rs.next()) {
            g = new Contact();
            g.setOrderNumber(rs.getString("orderNumber"));
            g.setHomeNumber(rs.getString("roomNumber"));
            g.setEndTime(rs.getDate("endTime"));
        }
        return g;
    }
}
