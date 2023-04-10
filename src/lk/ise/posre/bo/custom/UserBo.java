package lk.ise.posre.bo.custom;

import lk.ise.posre.bo.SuperBo;
import lk.ise.posre.dto.UserDto;

import java.sql.SQLException;

public interface UserBo extends SuperBo {
    public void initializeUsers() throws SQLException, ClassNotFoundException;
    public UserDto findUser(String username) throws Exception;
}
