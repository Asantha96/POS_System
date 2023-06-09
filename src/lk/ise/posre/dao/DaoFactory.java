package lk.ise.posre.dao;

import lk.ise.posre.dao.custom.OrderDetailDao;
import lk.ise.posre.dao.custom.impl.*;
import lk.ise.posre.enums.DaoType;

public class DaoFactory {
    private static DaoFactory daoFactory;
    private DaoFactory(){}
    public static DaoFactory getInstance(){
        return daoFactory==null ? (daoFactory=new DaoFactory()):daoFactory;
    }
    public <T> T getDao(DaoType type){
        switch (type){
            case CUSTOMER: return (T) new CustomerDaoImpl();
            case USER:return (T) new UserDaoImpl();
            case ITEM:return (T) new ItemDaoImpl();
            case ORDER:return (T) new OrderDaoImpl();
            case ORDER_DETAIL:return (T) new OrderDetailDaoImpl();
            default:return null;
        }
    }
}
