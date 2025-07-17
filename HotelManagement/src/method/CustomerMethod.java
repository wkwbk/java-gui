package method;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import hotel.DBUtil;
import model.Customer;

public class CustomerMethod {
    //插入条信息
    public void addCustomer(Customer g) throws Exception {
        Connection conn = DBUtil.getConnection();
        String SQL = "insert into customer_details(customerName,customerPassword,customerPhone,orderNumber)" + " values(?,?,?,?)";
        PreparedStatement stmt = conn.prepareStatement(SQL);
        stmt.setString(1, g.getCustomerName());
        stmt.setString(2, g.getCustomerPassword());
        stmt.setString(3, g.getCustomerPhone());
        stmt.setString(4, g.getOrderNumber());
        stmt.execute();
    }

    //
    public void updateCustomer2(Customer gg) throws Exception {
        Connection conn = DBUtil.getConnection();
        String SQL = "update customer_details set customerName=?,customerPassword=?,customerPhone=?,orderNumber=?" + " where customerID=?";
        PreparedStatement stmt = conn.prepareStatement(SQL);
        stmt.setString(1, gg.getCustomerName());
        stmt.setString(2, gg.getCustomerPassword());
        stmt.setString(3, gg.getCustomerPhone());
        stmt.setString(4, gg.getOrderNumber());
        stmt.setInt(5, gg.getCustomerID());
        stmt.execute();
    }

    //刷新一条信息
    public void updateCustomer(Customer g) throws Exception {
        Connection conn = DBUtil.getConnection();
        String SQL = "update customer_details set customerName=?,customerPassword=?,customerPhone=?,orderNumber=?" + " where customerName=?";
        PreparedStatement stmt = conn.prepareStatement(SQL);
        stmt.setString(1, g.getCustomerName());
        stmt.setString(2, g.getCustomerPassword());
        stmt.setString(3, g.getCustomerPhone());
        stmt.setString(4, g.getOrderNumber());
        stmt.setString(5, g.getCustomerName());
        stmt.execute();
    }

    //删除一条信息
    public void deleteCustomer(int id1) throws SQLException {
        Connection conn = DBUtil.getConnection();
        String SQL = "delete from customer_details " + "  where customerID=?";
        PreparedStatement stmt = conn.prepareStatement(SQL);
        stmt.setInt(1, id1);
        stmt.execute();
    }

    //返回表中所有信息到一个集合中
    public List<Customer> query() throws Exception {
        Connection conn = DBUtil.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select * from customer_details");
        List<Customer> gs = new ArrayList<>();
        Customer g;
        while (rs.next()) {
            g = new Customer();
            g.setCustomerID(rs.getInt("customerID"));
            g.setCustomerName(rs.getString("customerName"));
            g.setCustomerPassword(rs.getString("customerPassword"));
            g.seamyCall(rs.getString("customerPhone"));
            g.setOrderNumber(rs.getString("orderNumber"));
            gs.add(g);
        }
        return gs;
    }

    //查找所有姓名为name的信息
    public List<Customer> nameQuery(String name) throws Exception {
        List<Customer> gs = new ArrayList<>();

        Connection conn = DBUtil.getConnection();
        PreparedStatement stmt = conn.prepareStatement("select * from customer_details where customerName=?");

        stmt.setString(1, name);
        ResultSet rs = stmt.executeQuery();
        Customer g;
        while (rs.next()) {
            g = new Customer();
            g.setCustomerID(rs.getInt("customerID"));
            g.setCustomerName(rs.getString("customerName"));
            g.setCustomerPassword(rs.getString("customerPassword"));
            g.seamyCall(rs.getString("customerPhone"));
            g.setOrderNumber(rs.getString("orderNumber"));
            gs.add(g);
        }
        return gs;
    }

    //得到id的信息
    public Customer get(int id) throws Exception {
        Connection conn = DBUtil.getConnection();
        String SQL = "select customerID,customerName,customerPassword,customerPhone,orderNumber from customer_details" + " where customerID=?";
        PreparedStatement stmt = conn.prepareStatement(SQL);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        Customer g = null;
        while (rs.next()) {
            g = new Customer();
            g.setCustomerID(rs.getInt("customerID"));
            g.setCustomerName(rs.getString("customerName"));
            g.setCustomerPassword(rs.getString("customerPassword"));
            g.seamyCall(rs.getString("customerPhone"));
            g.setOrderNumber(rs.getString("orderNumber"));
        }
        return g;
    }
}
