package lanqiao.dao;

import lanqiao.bean.Purchase;

import java.sql.SQLException;

/**
 * @Author liangjinmei
 * @Date: 2022/12/03/ 17:34
 * @Version 1.0
 */
public interface PurchaseDao {
    // 入库
    void addPurchase(Purchase []purchase) throws SQLException;
}
