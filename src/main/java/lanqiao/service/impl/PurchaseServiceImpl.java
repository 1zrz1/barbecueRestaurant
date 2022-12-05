package lanqiao.service.impl;

import lanqiao.bean.Purchase;
import lanqiao.dao.PurchaseDao;
import lanqiao.dao.impl.PurchaseDaoImpl;
import lanqiao.service.PurchaseService;

import java.sql.SQLException;

/**
 * @Author liangjinmei
 * @Date: 2022/12/03/ 17:37
 * @Version 1.0
 */
public class PurchaseServiceImpl implements PurchaseService {
    private PurchaseDao purchaseDao=new PurchaseDaoImpl();

    @Override
    public void addPurchase(Purchase[] purchases) throws SQLException {
        purchaseDao.addPurchase(purchases);
    }
}
