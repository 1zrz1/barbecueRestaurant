package lanqiao.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @Author liangweifeng
 * @Date 2022/12/04
 * @Version 1.0
 */
public class IventoryDeleteDaoImpl {
    public void deleteIventory(String id){
        String sql ="DELETE FROM inverntory WHERE id=?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            pstmt=conn.prepareStatement(sql);
            pstmt.setString(1,id);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
