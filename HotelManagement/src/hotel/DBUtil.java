package hotel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBUtil {

    private static final Logger logger = LoggerFactory.getLogger(DBUtil.class);

    private static final String URL = "jdbc:mysql://debian:3306/hotel";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    private static Connection conn;

    static {
        try {
            // 1.加载驱动程序
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 2.获得数据库的连接
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            // 使用 SLF4J 记录错误信息
            logger.error("数据库连接初始化失败", e);  // 记录异常信息
        }
    }


    public static Connection getConnection() {
        return conn;
    }
}
