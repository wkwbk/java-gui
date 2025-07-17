package method;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import hotel.DBUtil;
import model.Room;

public class RoomMethod {
    public void addCustomer(Room g) throws Exception {
        Connection conn = DBUtil.getConnection();
        String SQL = "insert into room_details(roomNumber,roomType,roomPrice,roomState)" + " values(?,?,?,?)";
        PreparedStatement stmt = conn.prepareStatement(SQL);
        stmt.setString(1, g.getRoomNumber());
        stmt.setString(2, g.getRoomType());
        stmt.setInt(3, g.getRoomPrice());
        stmt.setInt(4, g.getRoomState());
        stmt.execute();
    }

    public void updateCustomer(String roomNumber) throws Exception {
        Connection conn = DBUtil.getConnection();
        String SQL = " update room_details set roomState=0" + " where roomNumber=?";
        PreparedStatement stmt = conn.prepareStatement(SQL);
        stmt.setString(1, roomNumber);
        stmt.execute();
    }

    public void updateCustomer(Room g) throws Exception {
        Connection conn = DBUtil.getConnection();
        String SQL = " update room_details set roomNumber=?,roomType=?,roomPrice=?,roomState=?" + " where roomNumber=?";
        PreparedStatement stmt = conn.prepareStatement(SQL);
        stmt.setString(1, g.getRoomNumber());
        stmt.setString(2, g.getRoomType());
        stmt.setInt(3, g.getRoomPrice());
        stmt.setInt(4, g.getRoomState());
        stmt.setString(5, g.getRoomNumber());
        stmt.execute();
    }

    public void deleteCustomer(String roomNumber) throws SQLException {
        Connection conn = DBUtil.getConnection();
        String SQL = "delete from room_details " + "  where roomNumber=?";
        PreparedStatement stmt = conn.prepareStatement(SQL);
        stmt.setString(1, roomNumber);
        stmt.execute();
    }

    public List<Room> query() throws Exception {
        Connection conn = DBUtil.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select roomNumber,roomType,roomPrice,roomState from room_details");
        List<Room> gs = new ArrayList<>();
        Room g;
        while (rs.next()) {
            g = new Room();
            g.setRoomNumber(rs.getString("roomNumber"));
            g.setRoomType(rs.getString("roomType"));
            g.setRoomPrice(rs.getInt("roomPrice"));
            g.setRoomState(rs.getInt("roomState"));
            gs.add(g);
        }
        return gs;
    }

    //按照房间类型查询
    public List<Room> nameQuery(String room) throws Exception {
        List<Room> gs = new ArrayList<>();

        Connection conn = DBUtil.getConnection();
        PreparedStatement stmt = conn.prepareStatement("select * from room_details where roomType=?");

        stmt.setString(1, room);
        ResultSet rs = stmt.executeQuery();
        Room g;
        while (rs.next()) {
            g = new Room();
            g.setRoomNumber(rs.getString("roomNumber"));
            g.setRoomType(rs.getString("roomType"));
            g.setRoomPrice(rs.getInt("roomPrice"));
            g.setRoomState(rs.getInt("roomState"));
            gs.add(g);
        }
        return gs;
    }

    public Room getRoom(String roomNumber) throws Exception {
        Connection conn = DBUtil.getConnection();
        String SQL = "select roomNumber,roomType,roomPrice,roomState from room_details" + " where roomNumber=?";
        PreparedStatement stmt = conn.prepareStatement(SQL);
        stmt.setString(1, roomNumber);
        ResultSet rs = stmt.executeQuery();
        Room g = null;
        while (rs.next()) {
            g = new Room();
            g.setRoomNumber(rs.getString("roomNumber"));
            g.setRoomType(rs.getString("roomType"));
            g.setRoomPrice(rs.getInt("roomPrice"));
            g.setRoomState(rs.getInt("roomState"));
        }
        return g;
    }
}
