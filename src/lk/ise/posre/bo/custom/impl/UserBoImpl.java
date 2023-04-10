package lk.ise.posre.bo.custom.impl;

import lk.ise.posre.bo.custom.UserBo;
import lk.ise.posre.dao.custom.CustomerDao;
import lk.ise.posre.dao.custom.UserDao;
import lk.ise.posre.dao.custom.impl.CustomerDaoImpl;
import lk.ise.posre.dao.custom.impl.UserDaoImpl;
import lk.ise.posre.dto.UserDto;
import lk.ise.posre.entity.User;

import java.sql.SQLException;

public class UserBoImpl implements UserBo {
    private UserDao userDao = new UserDaoImpl();
    @Override
    public void initializeUsers() throws SQLException, ClassNotFoundException {
        userDao.initializeUsers();

    }

    @Override
    public UserDto findUser(String userName) throws Exception {
        User user = userDao.find(userName);
        if (user!=null){
            return new UserDto(user.getUserName(),user.getPassword());
        }
        return null;
    }
}
