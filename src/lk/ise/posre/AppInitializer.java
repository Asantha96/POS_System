package lk.ise.posre;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lk.ise.posre.bo.BoFactory;
import lk.ise.posre.bo.custom.UserBo;
import lk.ise.posre.dao.DataAccessCode;
import lk.ise.posre.enums.BoType;

import java.io.IOException;
import java.sql.SQLException;

public class AppInitializer extends Application {
    private UserBo userBo = BoFactory.getInstance().getBo(BoType.USER);
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        try {
            userBo.initializeUsers();
        } catch (Exception e) {
            e.printStackTrace();
            return; //if there is a problem with app initializer don't go for jvm
        }
        primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("view/LoginForm.fxml"))));
        primaryStage.show();
    }
}
