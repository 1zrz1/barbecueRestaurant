package lanqiao.connection;

import lanqiao.bean.Users;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @Author 钟荣钊
 * @Date 2022/12/02
 * @V ersion 1.0
 */
public class Conn {
    /*
    传入SQL语句，返回结果集
     */
    public ResultSet getRsSet(String sql) throws SQLException, IOException {
        //读配置文件
        InputStream inputStream = Conn.class.getClassLoader().getResourceAsStream("config.properties");
        Properties properties = new Properties();
        properties.load(inputStream);
        String url = properties.getProperty("url");
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");

        Connection conn = DriverManager.getConnection(url, user, password);// 表示数据库的连接对象
        PreparedStatement pstmt = conn.prepareStatement(sql);// 表示SQL语句的对象
        ResultSet rs = pstmt.executeQuery();//执行SQL

        return rs;
    }


    //链接数据库
    public Connection getConnect() throws SQLException{
        //读配置文件
        InputStream inputStream = Conn.class.getClassLoader().getResourceAsStream("config.properties");
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String url = properties.getProperty("url");
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");

        Connection conn = DriverManager.getConnection(url, user, password);// 表示数据库的连接对象
        return conn;
    }



}