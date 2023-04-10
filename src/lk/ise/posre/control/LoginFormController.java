package lk.ise.posre.control;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ise.posre.bo.BoFactory;
import lk.ise.posre.bo.custom.UserBo;
import lk.ise.posre.dao.DataAccessCode;
import lk.ise.posre.db.Database;
import lk.ise.posre.dto.UserDto;
import lk.ise.posre.entity.User;
import lk.ise.posre.enums.BoType;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;

public class LoginFormController {
    public AnchorPane loginFormContext;
    public JFXButton btnSignIn;
    public JFXTextField txtUsername;
    public JFXPasswordField txtPassword;

    private UserBo bo = BoFactory.getInstance().getBo(BoType.USER);
    public void initialize(){


    }

    public void loginOnAction(ActionEvent actionEvent) throws IOException {
        try {
            UserDto selectedUser = bo.findUser(txtUsername.getText());
            if (selectedUser != null) {
                if (BCrypt.checkpw(txtPassword.getText(), selectedUser.getPassword())) {
                    //System.out.println("User logged");
                    Stage stage = (Stage) loginFormContext.getScene().getWindow();
                    stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashboardForm.fxml"))));
                    stage.centerOnScreen();
                } else {
                    System.out.println("wrong password");
                }
            } else {
                new Alert(Alert.AlertType.WARNING, "User not found").show();
            }
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,e.getMessage());
        }
    }
}
