package lanqiao.service;

import lanqiao.bean.Users;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * @Author 钟荣钊
 * @Date 2022/12/02
 * @Version 1.0
 */
public interface UserService {
    List<Users> getAllUser() throws SQLException, IOException;
}
