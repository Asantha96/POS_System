package lk.ise.posre.dao.custom;

import lk.ise.posre.dao.CrudDao;
import lk.ise.posre.entity.Order;

import java.sql.SQLException;

public interface OrderDao extends CrudDao<Order, String> {
    public String generateOrderId() throws SQLException, ClassNotFoundException;
}
