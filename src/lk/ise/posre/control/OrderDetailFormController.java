package lk.ise.posre.control;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ise.posre.db.Database;
import lk.ise.posre.entity.Order;
import lk.ise.posre.entity.OrderDetail;
import lk.ise.posre.view.tm.OrderDetailTM;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Stream;

public class OrderDetailFormController {

    public AnchorPane orderDetailContext;
    public JFXTextField txtOrderId;
    public JFXTextField txtCustomerName;
    public JFXTextField txtDate;
    public TableView tblOrderDetail;
    public TableColumn colItem;
    public TableColumn colDescription;
    public TableColumn colQty;
    public TableColumn colUnitPrice;
    public JFXTextField txtTotal;

    public void initialize(){
        colItem.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));

    }
    public void setOrder(String orderId){
        Optional<Order> order = Database.orders.stream().filter(e->e.getOrderId().equals(orderId)).findFirst();

        if(!order.isPresent()){
            new Alert(Alert.AlertType.WARNING,"Not Found").show();
            return;
        }
        txtOrderId.setText(order.get().getOrderId());
        txtCustomerName.setText(Database.customers.stream().filter(e->e.getId().equals(order.get().getCustomer())).findFirst().get().getName());
        txtTotal.setText(String.valueOf(order.get().getTotal()));
        txtDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(order.get().getDate()));
        loadTable(orderId);
    }
    public void loadTable(String orderId){
        //get order
        //extract order
        //load table---->ItemCode, description

        Optional<Order> selectedOrder = Database.orders.stream().filter(e -> e.getOrderId().equals(orderId)).findFirst();
        if(!selectedOrder.isPresent()){
            new Alert(Alert.AlertType.WARNING,"Not Found");
            return;
        }
        ArrayList<OrderDetail> items = null; //selectedOrder.get().getProducts();
        ObservableList<OrderDetailTM> tmList = FXCollections.observableArrayList();

        for (OrderDetail d:items
             ) {
            tmList.add(new OrderDetailTM(d.getCode(),Database.items.stream().filter(e->e.getCode().equals(d.getCode())).findFirst().get().getDescription(),d.getQty(),d.getUnitPrice()));
            
        }
        tblOrderDetail.setItems(tmList);

    }
}
