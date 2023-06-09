package lk.ise.posre.dao.custom.impl;

import lk.ise.posre.dao.CrudUtil;
import lk.ise.posre.dao.custom.OrderDao;
import lk.ise.posre.db.Database;
import lk.ise.posre.entity.Order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrderDaoImpl implements OrderDao {
    @Override
    public boolean save(Order order) throws Exception {
        return CrudUtil.execute("INSERT INTO orders VALUES(?,?,?,?)",
            order.getOrderId(),order.getCustomer(),order.getDate(),order.getTotal()
        );
    }

    @Override
    public boolean update(Order order) throws Exception {
        return false;
    }

    @Override
    public Order find(String s) throws Exception {
        return null;
    }

    @Override
    public boolean delete(String s) throws Exception {
        return false;
    }

    @Override
    public List<Order> findAll() throws Exception {
        return null;
    }

    @Override
    public String generateOrderId() throws SQLException, ClassNotFoundException {
        ResultSet set = CrudUtil.execute("SELECT order_id FROM orders ORDER BY order_id DESC LIMIT 1 ");
        if (set.next()){
            String lastOrderId = set.getString(1);
            int newOrderId = Integer.parseInt(lastOrderId.split("[A-Z]")[1])+1;
            return (String.format("D%03d",newOrderId));//returning requested next order id

        }else {
            return "D001";
        }

    }
}
