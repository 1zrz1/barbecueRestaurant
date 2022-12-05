package lanqiao.dao.impl;

import lanqiao.bean.Commodity;
import lanqiao.connection.Conn;
import lanqiao.dao.CommodityDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Author 李冰冰
 * @Date 2022/12/02
 * @Version 17.0.5
 */

public class CommodityDaoImpl implements CommodityDao {

    /*
       获取所有的商品信息
     */
    @Override
    public List<Commodity> getAllCommodity() throws SQLException{
        //连接数据库
        Conn conn = new Conn();
        Connection connection= conn.getConnect();
        PreparedStatement pstmt;
        //执行SQL
        String sql="SELECT * From `commodity`";
        pstmt = connection.prepareStatement(sql);// pstmt和sql语句做关联
        ResultSet rs = pstmt.executeQuery();
        List<Commodity> CommodityList = new ArrayList<>();
        //从结果集取数据
        while (rs.next()) {// 游标向下移动一次
            int id = rs.getInt("ID");
            String name = rs.getString("name");
            String sort = rs.getString("sort");
            int number = rs.getInt("number");
            int purchase = rs.getInt("purchase");
            int sell = rs.getInt("sell");
            String picAddress = rs.getString("picAddress");
            Commodity commodity=new Commodity(id,name,sort,number,purchase,sell,picAddress);
            CommodityList.add(commodity);
        }
        return CommodityList;
    }

    /*
        sortName：商品类别名称
        根据sortName计算各商品类别的数量
     */
    public int getSortNumber(String sortName) throws SQLException{
        //连接数据库
        Conn conn = new Conn();
        Connection connection= conn.getConnect();
        PreparedStatement pstmt;
        //执行SQL
        String sql="SELECT COUNT(*) sort FROM `commodity` WHERE sort=?";
        pstmt = connection.prepareStatement(sql);// pstmt和sql语句做关联
        pstmt = connection.prepareStatement(sql);// pstmt和sql语句做关联
        pstmt.setString(1,sortName); // 插入第一个？符中
        ResultSet rs = pstmt.executeQuery();
        int SortNumber=0;
        if(rs.next()){
            SortNumber=rs.getInt("sort");
        }
        return SortNumber;
    }

    /*
       根据sortName商品类别，获取商品名字，单价，图片地址
     */
    public Object[][] getSortpicAdress(String sortName) throws SQLException{
        CommodityDaoImpl cdi=new CommodityDaoImpl();
        int size=getSortNumber(sortName); //获取该商品类别的数量
        Object [][]data = new Object[size][3];
        List<Commodity> list=getAllCommodity();
        int i=0;
        for (Commodity commodity : list) {
            if (Objects.equals(commodity.getSort(), sortName)) {
                data[i][0] = commodity.getName();
                data[i][1] =commodity.getSell();
                data[i][2] =commodity.getPicAddress();
                i++;
            }
        }
        return data;
    }


    // 测试
    public static void main(String[] args) {
        CommodityDaoImpl cdi=new CommodityDaoImpl();
        try {
            Object [][]data=cdi.getSortpicAdress("烤串");
            for(int i=0;i<data.length;i++){
                System.out.println(data[i][0]+" "+data[i][1]+" "+data[i][2]);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
