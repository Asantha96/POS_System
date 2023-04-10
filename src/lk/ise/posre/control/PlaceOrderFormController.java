package lk.ise.posre.control;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ise.posre.bo.BoFactory;
import lk.ise.posre.bo.custom.CustomerBo;
import lk.ise.posre.bo.custom.ItemBo;
import lk.ise.posre.bo.custom.OrderBo;
import lk.ise.posre.db.Database;
import lk.ise.posre.dto.CustomerDto;
import lk.ise.posre.dto.ItemDto;
import lk.ise.posre.dto.OrderDetailDto;
import lk.ise.posre.dto.OrderDto;
import lk.ise.posre.entity.Customer;
import lk.ise.posre.entity.Item;
import lk.ise.posre.entity.Order;
import lk.ise.posre.entity.OrderDetail;
import lk.ise.posre.enums.BoType;
import lk.ise.posre.view.tm.CartTM;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class PlaceOrderFormController {

    public AnchorPane placeOrderFormContext;
    public JFXButton btnBackToHome;
    public JFXComboBox cmbCustomerId;
    public JFXTextField txtName;
    public JFXTextField txtAddress;
    public JFXTextField txtSalary;
    public JFXComboBox cmbItemCode;
    public JFXTextField txtDescription;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtQtyOnHand;
    public JFXTextField txtRequestQty;
    public JFXButton btnAddToCart;
    public JFXButton btnRemoveCart;
    public TableView tblCart;
    public Label lblTotal;
    public JFXButton btnPlaceOrder;
    public TableColumn colItemCode;
    public TableColumn colDescription;
    public TableColumn colUnitPrice;
    public TableColumn colQty;
    public TableColumn colTotal;
    public TableColumn colOption;
    public JFXButton btnAddNewCustomer;
    public Label lblDateAndTime;
    public Label lblOrderDate;
    public Label lblOrderId;
    private CustomerBo customerBo = BoFactory.getInstance().getBo(BoType.CUSTOMER);
    private ItemBo itemBo = BoFactory.getInstance().getBo(BoType.ITEM);
    private OrderBo orderBo = BoFactory.getInstance().getBo(BoType.ORDER);

    public void initialize() throws SQLException, ClassNotFoundException {
        manageDateAndTime();

        //////////////
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));
        //////////////

        loadCustomerIds();
        loadItemCodes();
        loadOrderId();

        cmbCustomerId.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue)->{
            if (newValue!=null){
                try {
                    setCustomerData((String) newValue);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        cmbItemCode.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue)->{
            if (newValue!=null){
                try {
                    setItemData((String) newValue);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void loadOrderId() throws SQLException, ClassNotFoundException {
        /*if(Database.orders.size()>0){
            Order order = Database.orders.get(Database.orders.size()-1);
            String lastOrderId = order.getOrderId();
            String splitId = lastOrderId.split("[A-Z]")[1];
            int i = Integer.parseInt(splitId);
            i++;
            String newOrderId = String.format("D%03d",i);
            lblOrderId.setText(newOrderId);
            int newOrderId = Integer.parseInt(Database.orders.get(Database.orders.size()-1).getOrderId().split("[A-Z]")[1])+1;
            lblOrderId.setText(String.format("D%03d",newOrderId));
        }else{
        }*/
        lblOrderId.setText(orderBo.generateOrderId());
    }

    private void manageDateAndTime() {
        Timeline timeAndDate = new Timeline(new KeyFrame(Duration.ZERO, event -> lblDateAndTime.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))),new KeyFrame(Duration.seconds(1)));
        timeAndDate.setCycleCount(Animation.INDEFINITE);
        timeAndDate.play();
        lblOrderDate.setText(String.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(new Date())));
    }


    private void setCustomerData(String id) throws Exception {
        CustomerDto customer = customerBo.findCustomer(id);
        if (customer!=null){
            txtName.setText(customer.getName());
            txtAddress.setText(customer.getAddress());
            txtSalary.setText(String.valueOf(customer.getSalary()));
        }else {
            new Alert(Alert.AlertType.WARNING,"Not found").show();
        }
    }
    private void setItemData(String code) throws Exception {
        ItemDto item = itemBo.findItem(code);
        if (item!=null){
            txtDescription.setText(item.getDescription());
            txtUnitPrice.setText(String.valueOf(item.getUnitPrice()));
            txtQtyOnHand.setText(String.valueOf(item.getQtyOnHand()));
        }else {
            new Alert(Alert.AlertType.WARNING,"Not found");
        }
    }

    private void loadCustomerIds() throws SQLException, ClassNotFoundException {

        ObservableList<String> obList = FXCollections.observableArrayList(
                customerBo.loadCustomerIds()
        );
        cmbCustomerId.setItems(obList);
    }
    private void loadItemCodes() throws SQLException, ClassNotFoundException {
        ObservableList<String> obList = FXCollections.observableArrayList(
                itemBo.loadItemCodes()
        );
        cmbItemCode.setItems(obList);

        for (Item item:Database.items
        ) {
            cmbItemCode.getItems().add(item.getCode());

        }
    }

    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) placeOrderFormContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashboardForm.fxml"))));
        stage.centerOnScreen();
    }

    ObservableList<CartTM> tmList = FXCollections.observableArrayList();
    public void addToCartOnAction(ActionEvent actionEvent) {
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        int qty = Integer.parseInt(txtRequestQty.getText());
        double total = unitPrice*qty;

        if (isItemExistOnCart(cmbItemCode.getValue())) {
            for (CartTM t:tmList
                 ) {
                if(t.getCode().equals(cmbItemCode.getValue())){
                    t.setQty(t.getQty()+qty);
                    t.setTotal(t.getTotal()+total);
                    tblCart.refresh();
                }

            }

        }else {
            Button btn = new Button("Delete");
            CartTM tm = new CartTM(cmbItemCode.getValue().toString(),txtDescription.getText(),unitPrice,qty,total,btn);

            btn.setOnAction(event -> {
                tmList.remove(tm);
                tblCart.refresh();
                calculateTotal();
            });
            tmList.add(tm);
            tblCart.setItems(tmList);
        }
        clearItemData();
        calculateTotal();
    }

    private void calculateTotal() {
        double tot = 0;
        for (CartTM t:tmList
             ) {
            tot += t.getTotal();
        }
        lblTotal.setText(String.valueOf(tot));
    }

    private boolean isItemExistOnCart(Object code) {
        for (CartTM t:tmList
             ) {
            if (t.getCode().equals(code)){
                return true;
            }
        }

        return false;
    }

    private void clearItemData() {
        cmbItemCode.setValue(null);
        txtDescription.clear();
        txtUnitPrice.clear();
        txtQtyOnHand.clear();
        txtRequestQty.clear();
    }

    public void removeCartOnAction(ActionEvent actionEvent) {

        tmList.removeAll();
        tblCart.refresh();

    }

    /////save order
    public void placeOrderOnAction(ActionEvent actionEvent) throws Exception {
        ArrayList<OrderDetailDto> products = new ArrayList<>();
        for (CartTM tm:tmList
             ) {
            products.add(new OrderDetailDto(tm.getCode(),lblOrderId.getText(), tm.getUnitPrice(),tm.getQty()));
            manageQty(tm.getCode(),tm.getQty());

        }
        /*String customerId = (String) cmbCustomerId.getValue();
        System.out.println(customerId);*/

        OrderDto orderDto = new OrderDto(lblOrderId.getText(),(String) cmbCustomerId.getValue(), new Date(),Double.parseDouble(lblTotal.getText()));
        boolean isSaved = orderBo.saveOrder(orderDto,products);
        if (isSaved){
            new Alert(Alert.AlertType.INFORMATION,"Order completed").show();
            tmList.clear();
            tblCart.refresh();
            lblTotal.setText(String.valueOf(0));
            loadOrderId();
            clearCustomerData();
        }else {
            new Alert(Alert.AlertType.ERROR,"Try Again").show();
        }
    }

    //////////////////////////////////////////////////////////////
    private void clearCustomerData() {
        cmbCustomerId.getSelectionModel().clearSelection();
        txtName.clear();
        txtSalary.clear();
        txtAddress.clear();
    }

    private void manageQty(String code, int qty) {

        for (Item i:Database.items
             ) {
            if (i.getCode().equals(code)){
                i.setQtyOnHand(i.getQtyOnHand()-qty);
                return;
            }

        }
    }

    public void requestQuantityOnAction(ActionEvent actionEvent) {
        addToCartOnAction(actionEvent);
    }

    public void addNewCustomerOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) placeOrderFormContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/CustomerForm.fxml"))));
        stage.centerOnScreen();
    }
}

