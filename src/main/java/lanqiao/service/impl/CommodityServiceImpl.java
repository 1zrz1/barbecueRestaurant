package lanqiao.service.impl;

import lanqiao.bean.Commodity;
import lanqiao.dao.CommodityDao;
import lanqiao.dao.impl.CommodityDaoImpl;
import lanqiao.service.CommodityService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * @Author 李冰冰
 * @Date 2022/12/02
 * @Version 17.0.5
 */

public class CommodityServiceImpl implements CommodityService {
    private CommodityDao commodityDao = new CommodityDaoImpl();

    @Override
    public List<Commodity> getAllCommodity() throws SQLException {
        try {
            return commodityDao.getAllCommodity();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getSortNumber(String sortName) throws SQLException {
        try {
            return commodityDao.getSortNumber(sortName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public Object[][] getSortpicAdress(String sortName) throws SQLException {
        try {
            return commodityDao.getSortpicAdress(sortName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
       测试方法
   */
    public static void main(String[] args) throws SQLException{
        CommodityService commodityService = new CommodityServiceImpl();
        System.out.println(commodityService.getAllCommodity());
    }
}
