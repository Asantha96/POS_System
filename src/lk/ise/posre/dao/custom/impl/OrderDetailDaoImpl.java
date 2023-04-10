package lk.ise.posre.dao.custom.impl;

import lk.ise.posre.dao.CrudUtil;
import lk.ise.posre.dao.custom.OrderDetailDao;
import lk.ise.posre.entity.OrderDetail;

import java.util.List;

public class OrderDetailDaoImpl implements OrderDetailDao {

    @Override
    public boolean save(OrderDetail orderDetail) throws Exception {
        return CrudUtil.execute("INSERT INTO order_detail VALUES(?,?,?,?)",orderDetail.getCode(),orderDetail.getOrderId(),orderDetail.getQty(), orderDetail.getUnitPrice());
    }

    @Override
    public boolean update(OrderDetail orderDetail) throws Exception {
        return false;
    }

    @Override
    public OrderDetail find(String s) throws Exception {
        return null;
    }

    @Override
    public boolean delete(String s) throws Exception {
        return false;
    }

    @Override
    public List<OrderDetail> findAll() throws Exception {
        return null;
    }
}
