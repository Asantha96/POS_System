package lk.ise.posre.dao;

import lk.ise.posre.db.DBConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CrudUtil {
    //Time inference ========> dont know the type of the return type but jvm identify it dynamically the type when it is storing
    public static <T> T execute(String sql,Object...params) throws SQLException, ClassNotFoundException {
        ////Object...params(Varargs) that is number of arguments are varying
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            pstm.setObject((i+1),params[i]);
        }
        if (sql.startsWith("SELECT")){
            return (T)pstm.executeQuery();
        }
        return (T)(Boolean)(pstm.executeUpdate()>0);


    }
}
