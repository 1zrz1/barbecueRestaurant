package lanqiao.dao.impl;

import lanqiao.dao.IventoryInsertDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @Author liangweifeng
 * @Date 2022/12/04
 * @Version 1.0
 */
public class IventoryInsertDaoImpl implements IventoryInsertDao {
    public void addIventory(String id,String name,String number,String unit){
        String sql ="INSERT INTO inventory VALUE(?,?,?,?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            pstmt=conn.prepareStatement(sql);
            pstmt.setString(1,id);
            pstmt.setString(2,name);
            pstmt.setString(3,number);
            pstmt.setString(4,unit);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
