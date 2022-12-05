package lanqiao.service.impl;

import lanqiao.dao.IventoryInsertDao;
import lanqiao.dao.impl.IventoryInsertDaoImpl;
import lanqiao.service.IventoryInsertService;

/**
 * @Author liangweifeng
 * @Date 2022/12/04
 * @Version 1.0
 */
public class IventoryInsertServiceImpl implements IventoryInsertService {
    private IventoryInsertDao insertDao=new IventoryInsertDaoImpl();
    @Override
    public void  addIventory(String id,String name,String number,String unit){
        insertDao.addIventory(id,name,number,unit);
    }
}
