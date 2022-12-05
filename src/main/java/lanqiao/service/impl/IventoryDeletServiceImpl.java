package lanqiao.service.impl;

import lanqiao.dao.IventoryDeleteDao;
import lanqiao.service.IventoryDeletService;

/**
 * @Author liangweifeng
 * @Date 2022/12/04
 * @Version 1.0
 */
public class IventoryDeletServiceImpl implements IventoryDeletService {
    public IventoryDeleteDao deleteDao = new IventoryDeleteDao() {
        @Override
        public void deleteIventory(String id) {
            deleteDao.deleteIventory(id);
        }
    };

    @Override
    public void deletIventory(String id) {

    }
}




