package lk.ise.posre.dao.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordConfig {
    public String encryptPassword(String rowPassword){
        //mechanism to return encrypted password;
        return BCrypt.hashpw(rowPassword,BCrypt.gensalt());

    }
}
