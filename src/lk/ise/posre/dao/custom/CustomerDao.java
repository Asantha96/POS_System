package lk.ise.posre.dao.custom;

import lk.ise.posre.dao.CrudDao;
import lk.ise.posre.entity.Customer;

import java.sql.SQLException;
import java.util.List;

public interface CustomerDao extends CrudDao<Customer, String> {
    public List<String> loadCustomerIds() throws SQLException, ClassNotFoundException;
}
