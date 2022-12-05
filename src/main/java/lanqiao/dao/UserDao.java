package lanqiao.dao;

import lanqiao.bean.Users;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * @Author 钟荣钊
 * @Date 2022/12/02
 * @Version 1.0
 */
public interface UserDao {
    List<Users> getAllUser() throws SQLException, IOException;
     List<Users> getUser(String sql2)throws SQLException;
    boolean login(String username, String password,String type);
    void login(String sql) throws SQLException;
    void pstmt(int o, String b[][],String v[]) throws SQLException;
}
