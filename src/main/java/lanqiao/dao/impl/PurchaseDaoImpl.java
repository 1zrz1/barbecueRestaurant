package lanqiao.dao.impl;

import lanqiao.bean.Purchase;
import lanqiao.connection.Conn;
import lanqiao.dao.PurchaseDao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 * @Author 梁金梅
 * @Date: 2022/12/03/ 12:57
 * @Version 1.0
 */
public class PurchaseDaoImpl implements PurchaseDao{
    // 加入库存表
    public void addPurchase(Purchase[] purchases) throws SQLException{
        Conn conn=new Conn();
        String sql1 = "INSERT INTO purchase(name,unit,purchase,number,sell,expiration,supplier,date) VALUES(?,?,?,?,?,?,?,?) ";// 添加语句
        String sql2="INSERT INTO commoditys(name,number,purchase,sell) VALUES(?,?,?,?)";
        Connection connection =conn.getConnect();
        PreparedStatement pstmt1 = null;// 表示SQL语句的对象;
        PreparedStatement pstmt2=null;
        try {
            for (Purchase purchase:purchases){
                pstmt1 = connection.prepareStatement(sql1);//把pstmt1和sql语句做一个关联
                pstmt1.setString(1, purchase.getName());
                pstmt1.setString(2, purchase.getUnit());
                pstmt1.setDouble(3, purchase.getPurchase());
                pstmt1.setInt(4, purchase.getNumber());
                pstmt1.setDouble(5,purchase.getSell());
                pstmt1.setInt(6,purchase.getExpiration());
                pstmt1.setString(7,purchase.getSupplier());
                pstmt1.setDate(8,purchase.getDate());
                pstmt1.executeUpdate();//执行SQL语句

                pstmt2 = connection.prepareStatement(sql2);
                pstmt2.setString(1,purchase.getName());
                pstmt2.setInt(2,purchase.getNumber());
                pstmt2.setDouble(3,purchase.getPurchase());
                pstmt2.setDouble(4,purchase.getSell());
                pstmt2.executeUpdate();//执行SQL语句
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                pstmt1.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
