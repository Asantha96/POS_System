package lk.ise.posre.control;

import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DashboardFormController {
    public AnchorPane dashboardFormContext;
    public Label lblDateAndTime;
    public JFXButton btnLogout;
    public JFXButton btnCustomer;
    public JFXButton btnItem;
    public JFXButton btnIncome;
    public JFXButton btnOrder;
    public JFXButton btnNewOrder;

    public void initialize(){
        manageDateAndTime();
    }

    private void manageDateAndTime() {
        Timeline timeAndDate = new Timeline(new KeyFrame(Duration.ZERO,event -> lblDateAndTime.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))),new KeyFrame(Duration.seconds(1)));
        timeAndDate.setCycleCount(Animation.INDEFINITE);
        timeAndDate.play();
    }

    public void logoutOnAction(ActionEvent actionEvent) throws IOException {
            setUi("LoginForm");
    }

    public void openCustomerFormOnAction(ActionEvent actionEvent) throws IOException {
        setUi("CustomerForm");
    }

    public void openItemFormOnAction(ActionEvent actionEvent) throws IOException {
        setUi("ItemForm");
    }

    public void openIncomeFormOnAction(ActionEvent actionEvent) {
    }

    public void openOrderFormOnAction(ActionEvent actionEvent) throws IOException {
        setUi("OrderForm");
    }

    public void openNewOrderFormOnAction(ActionEvent actionEvent) throws IOException {
        setUi("PlaceOrderForm");
    }
    private void setUi(String location) throws IOException {
        Stage stage = (Stage) dashboardFormContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+location+".fxml"))));
        stage.centerOnScreen();
    }
}
