package lk.ise.posre.dao.custom.impl;

import lk.ise.posre.bo.BoFactory;
import lk.ise.posre.bo.custom.ItemBo;
import lk.ise.posre.bo.custom.OrderBo;
import lk.ise.posre.dao.DaoFactory;
import lk.ise.posre.dao.custom.OrderDao;
import lk.ise.posre.dao.custom.OrderDetailDao;
import lk.ise.posre.db.DBConnection;
import lk.ise.posre.dto.OrderDetailDto;
import lk.ise.posre.dto.OrderDto;
import lk.ise.posre.entity.Order;
import lk.ise.posre.entity.OrderDetail;
import lk.ise.posre.enums.BoType;
import lk.ise.posre.enums.DaoType;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class OrderBoImpl implements OrderBo {
    private OrderDao orderDao = DaoFactory.getInstance().getDao(DaoType.ORDER);
    private OrderDetailDao orderDetailDao = DaoFactory.getInstance().getDao(DaoType.ORDER_DETAIL);
    private ItemBo itemBo = BoFactory.getInstance().getBo(BoType.ITEM);
    @Override
    public boolean saveOrder(OrderDto dto, List<OrderDetailDto> orderDetail) throws Exception {
        Connection connection = DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false);
        try {
            boolean isOrderSaved = orderDao.save(new Order(dto.getOrderId(), dto.getCustomer(), dto.getDate(), dto.getTotal()));

            if (isOrderSaved) {
                for (OrderDetailDto d : orderDetail
                ) {
                    boolean isItemSaved = orderDetailDao.save(new OrderDetail(d.getCode(), d.getOrderId(), d.getUnitPrice(), d.getQty()));
                    if (isItemSaved) {
                        //update quantity
                        boolean b = itemBo.updateQty(d.getCode(), d.getQty());
                        if (!b) {
                            connection.rollback();
                            return false;
                        }

                    } else {
                        connection.rollback();
                        return false;
                    }
                }

            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            connection.commit();
            connection.setAutoCommit(true);
        }
        return true;
    }

    @Override
    public String generateOrderId() throws SQLException, ClassNotFoundException {
        return orderDao.generateOrderId();
    }
}
