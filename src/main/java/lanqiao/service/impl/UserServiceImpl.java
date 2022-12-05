package lanqiao.service.impl;

import lanqiao.bean.Users;
import lanqiao.dao.UserDao;
import lanqiao.dao.impl.UserDaoImpl;
import lanqiao.service.UserService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * @Author 钟荣钊
 * @Date 2022/12/02
 * @Version 1.0
 */
public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();

    @Override
    public List<Users> getAllUser() throws SQLException, IOException {
        return userDao.getAllUser();
    }

    @Override
    public List<Users> getUser(String sql2) throws SQLException {
        return userDao.getUser(sql2);
    }

    @Override
    public void login(String sql1) throws SQLException {
         userDao.login(sql1);
    }

    @Override
    public void pstmt(int o, String b[][],String v[]) throws SQLException {
        userDao.pstmt( o, b, v);
    }
    @Override
    public boolean login(String username, String password,String type) {
        return userDao.login(username,password,type);
    }

    /*
    测试方法
     */
    public static void main(String[] args) throws SQLException, IOException {
        UserService userService = new UserServiceImpl();
        System.out.println(userService.getAllUser());
    }
}
