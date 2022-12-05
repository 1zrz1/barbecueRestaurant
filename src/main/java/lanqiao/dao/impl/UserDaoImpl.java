package lanqiao.dao.impl;

import lanqiao.bean.Users;
import lanqiao.connection.Conn;
import lanqiao.dao.UserDao;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author 钟荣钊
 * @Date 2022/12/02
 * @Version 1.0
 */
public class UserDaoImpl implements UserDao {
    /*
    @Override
    public List<Users> getAllUser() throws SQLException, IOException {
        //连接数据库
        Conn conn = new Conn();
        //执行SQL
        ResultSet rs = conn.getRsSet("SELECT * FROM users");
        //结果集

        List<Users> usersList = new ArrayList<>();
        //从结果集取数据
        while (rs.next()) {// 游标向下移动一次
            int id = rs.getInt("ID");
            String username = rs.getString("USERNAME");
            String password = rs.getString("PASSWORD");
            Users user = new Users(id, username, password);
            usersList.add(user);
        }
        return usersList;

        /* 测试更改 */
  //  }

    @Override
    public List<Users> getAllUser() throws SQLException, IOException {
        String sql = "SELECT * FROM users";
        String url = "jdbc:mysql://120.78.211.33:3306/barbecueRestaurant?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        Connection conn = null;
        PreparedStatement pstmt = null;//表示SQL语句的对象
        ResultSet rs = null;

            conn = DriverManager.getConnection(url, "root", "Aliyun1154615101@");
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            List<Users> usersList =new ArrayList<Users>();
            //从结果集取数据
            while (rs.next()) {

                int id = rs.getInt(1);
                String username = rs.getString(2);
                String password = rs.getString(3);
                String type = rs.getString(4);
                //System.out.println(id + "\t" + username + "\t" + password + "\t" + addtress);
                Users user =new Users(id,username,password,type);
                usersList.add(user);
            }
            return usersList;
    }

    public void login(String sql1) throws SQLException {
        String url = "jdbc:mysql://120.78.211.33:3306/barbecueRestaurant?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        String sql = sql1;
        Statement stmt=null;
        Connection conn = DriverManager.getConnection(url, "root", "Aliyun1154615101@");
        stmt = conn.createStatement();
        try {
            stmt.executeUpdate(sql1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Users> getUser(String sql2) throws SQLException {
        String url = "jdbc:mysql://120.78.211.33:3306/barbecueRestaurant?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        String sql = sql2;
        Statement stmt=null;
        Connection conn = DriverManager.getConnection(url, "root", "123456");
        PreparedStatement pstmt = null;//表示SQL语句的对象
        ResultSet rs = null;

        conn = DriverManager.getConnection(url, "root", "Aliyun1154615101@");
        pstmt = conn.prepareStatement(sql);
        rs = pstmt.executeQuery();

        List<Users> userList =new ArrayList<Users>();
        //从结果集取数据
        while (rs.next()) {

            int id = rs.getInt(1);
            String username = rs.getString(2);
            String password = rs.getString(3);
            String type = rs.getString(4);
            Users user =new Users(id,username,password,type);
            userList.add(user);
        }
        return userList;
    }

    public void pstmt(int o, String b[][],String v[]) throws SQLException {
        String url = "jdbc:mysql://120.78.211.33:3306/barbecueRestaurant?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        String sql2 = "INSERT into users(id,username,password) VALUES(?,?,?)";
        Connection conn = DriverManager.getConnection(url, "root", "Aliyun1154615101@");
        PreparedStatement ppstmt  = conn.prepareStatement(sql2);


        while( o<b.length){
            //   System.out.println(o);
            for( int p=0;p<b[o].length;p++){
                v[p]= (String) b[o][p];
            }
            /*
            for(int  p=0;p<b[o].length;p++){
                System.out.println(v[p]);
            }

             */
       //     System.out.println(v.length);
            ppstmt.setInt(1, Integer.parseInt(v[0]));
            for(int p=1;p<v.length;p++){
                ppstmt.setString(p+1,v[p]);
            }
            ppstmt.executeUpdate();
            o++;
        }
    }

    public boolean login(String username, String password,String type) {
        String sql = "SELECT password,type FROM users where username='"+username+"'";
        String url = "jdbc:mysql://120.78.211.33:3306/barbecueRestaurant?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        Connection conn = null;
        PreparedStatement pstmt = null;//表示SQL语句的对象
        ResultSet rs = null;
        boolean zhen = false;
        try {
            conn = DriverManager.getConnection(url, "root", "Aliyun1154615101@");
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

       //     PasswordEncoder encoderMd5 = new PasswordEncoder("6e9e4676d01c434da03d1aaf45c7413e", "MD5");
       //     String zpassword = encoderMd5.encode(password);


            while(rs.next()){
                String password1 = rs.getString("password");
                String type1 = rs.getString("type");
                if (password1.equals(password)&&type1.equals(type)) {
                    zhen= true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return zhen;
    }/*


    /*
    测试用例
     */
    public static void main(String[] args) throws SQLException, IOException {
        UserDao userDao = new UserDaoImpl();
        System.out.println(userDao.getAllUser());
    }
}
