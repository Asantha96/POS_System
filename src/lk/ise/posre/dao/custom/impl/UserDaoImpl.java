package lk.ise.posre.dao.custom.impl;

import lk.ise.posre.dao.CrudUtil;
import lk.ise.posre.dao.custom.UserDao;
import lk.ise.posre.dao.util.PasswordConfig;
import lk.ise.posre.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDaoImpl implements UserDao {
    @Override
    public boolean save(User user) throws Exception {
        return false;
    }

    @Override
    public boolean update(User user) throws Exception {
        return false;
    }

    @Override
    public User find(String userName) throws Exception {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM user WHERE userName=?", userName);
        if(resultSet.next()){
            return new User(resultSet.getString(1),resultSet.getString(2));
        }
        return null;
    }

    @Override
    public boolean delete(String s) throws Exception {
        return false;
    }

    @Override
    public List<User> findAll() throws Exception {
        return null;
    }

    @Override
    public void initializeUsers() throws SQLException, ClassNotFoundException {
        ResultSet countSet = CrudUtil.execute("SELECT COUNT(*) FROM user");
        if (countSet.next()){
            int count = countSet.getInt(1);
            if (count==0){
                User user1 = new User("linda",new PasswordConfig().encryptPassword("1234"));
                CrudUtil.execute("INSERT INTO user VALUES(?,?)",user1.getUserName(),user1.getPassword());
                User user2 = new User("anna",new PasswordConfig().encryptPassword("1234"));
                CrudUtil.execute("INSERT INTO user VALUES(?,?)",user2.getUserName(),user2.getPassword());
                User user3 = new User("tom",new PasswordConfig().encryptPassword("1234"));
                CrudUtil.execute("INSERT INTO user VALUES(?,?)",user3.getUserName(),user3.getPassword());
            }
        }
    }
}
