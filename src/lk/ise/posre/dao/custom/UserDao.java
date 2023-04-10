package lk.ise.posre.dao.custom;

import lk.ise.posre.dao.CrudDao;
import lk.ise.posre.entity.User;

import java.sql.SQLException;

public interface UserDao extends CrudDao<User, String> {
    public void initializeUsers() throws SQLException, ClassNotFoundException;
}
