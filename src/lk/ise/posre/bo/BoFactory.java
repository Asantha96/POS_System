package lk.ise.posre.bo;

import lk.ise.posre.bo.custom.impl.CustomerBoImpl;
import lk.ise.posre.bo.custom.impl.ItemBoImpl;
import lk.ise.posre.bo.custom.impl.UserBoImpl;
import lk.ise.posre.dao.custom.impl.OrderBoImpl;
import lk.ise.posre.enums.BoType;

public class BoFactory {
    private static BoFactory boFactory;
    private BoFactory(){}
    public static BoFactory getInstance(){
        return boFactory==null ? boFactory=new BoFactory():boFactory;
    }
    public <T> T getBo(BoType type){
        switch (type){
            case CUSTOMER:return (T) new CustomerBoImpl();
            case USER:return (T) new UserBoImpl();
            case ITEM:return (T) new ItemBoImpl();
            case ORDER:return (T) new OrderBoImpl();
            default:return null;
        }
    }


}
