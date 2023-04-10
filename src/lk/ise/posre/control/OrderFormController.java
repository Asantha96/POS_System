package lk.ise.posre.control;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ise.posre.db.Database;
import lk.ise.posre.entity.Order;
import lk.ise.posre.view.tm.OrderTM;

import java.io.IOException;
import java.text.SimpleDateFormat;

public class OrderFormController {
    public AnchorPane orderFormContext;
    public JFXButton btnBackToHome;
    public TableView<OrderTM> tblOrder;
    public TableColumn colOrderId;
    public TableColumn colCustomerName;
    public TableColumn colCost;
    public TableColumn colDate;

    public void initialize(){
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        
        loadData();

        tblOrder.getSelectionModel().selectedItemProperty().addListener(((observable,oldValue,newValue)->{
            if (newValue!=null){
                try {
                    loadDetail(newValue.getId());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }));
    }

    private void loadDetail(String id) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/OrderDetailForm.fxml"));
        Parent load = loader.load();
        OrderDetailFormController controller = loader.getController();
        controller.setOrder(id);
        Stage stage = new Stage();
        stage.setScene(new Scene(load));
        stage.show();
        stage.centerOnScreen();
    }

    private void loadData() {
        ObservableList<OrderTM> obList = FXCollections.observableArrayList();
        for (Order o: Database.orders
             ) {
            //String customerName = Database.customers.stream().filter(e->e.getId().equals(o.getCustomer())).findFirst().get().getName();

            obList.add(new OrderTM(o.getOrderId(),
                    Database.customers.stream().filter(e->e.getId().equals(o.getCustomer())).findFirst().get().getName(),
                    o.getTotal(),new SimpleDateFormat("yyyy-MM-dd").format(o.getDate())));
            //Database.customers.stream().filter(e->e.getId().equals(o.getCustomer())).findFirst().get().getName()
            
        }
        tblOrder.setItems(obList);
    }

    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage =(Stage) orderFormContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashboardForm.fxml"))));
        stage.centerOnScreen();
    }



}
