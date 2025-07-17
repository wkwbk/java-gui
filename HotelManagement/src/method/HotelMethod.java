package method;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import hotel.DBUtil;
import model.Hotel;

public class HotelMethod {
    // 刷新一条信息
    public void updateHotelInfo(Hotel g) throws Exception {
        Connection conn = DBUtil.getConnection();
        String SQL = "update hotel_details set hotelName=?,hotelAddress=?,hotelPhone=?";
        PreparedStatement stmt = conn.prepareStatement(SQL);
        stmt.setString(1, g.getHotelName());
        stmt.setString(2, g.getHotelAddress());
        stmt.setString(3, g.getHotelPhone());
        stmt.execute();
    }

    // 返回表中所有信息到一个集合中
    public List<Hotel> query() throws Exception {
        Connection conn = DBUtil.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select * from hotel_details");
        List<Hotel> gs = new ArrayList<>();
        Hotel g;
        while (rs.next()) {
            g = new Hotel();
            g.setHotelName(rs.getString("hotelName"));
            g.setHotelAddress(rs.getString("hotelAddress"));
            g.setHotelPhone(rs.getString("hotelPhone"));
            gs.add(g);
        }
        return gs;
    }
}
